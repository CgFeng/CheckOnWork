package com.chengang.newcheck.http;

import android.content.Context;
import android.util.Log;

import com.chengang.newcheck.bean.AttendInfo;
import com.chengang.newcheck.bean.PhotoUploadTest;
import com.chengang.newcheck.bean.UploadMsg;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by urcha on 2015/04/16.
 */
public class AttendHttpHelper extends BaseHttpHelper {
    private static final ObservableHelper observableHelper = new ObservableHelper();

    public static void submit(final AttendInfo attendInfo, final Observer observer) {
        String targetUrl = BASEURL + "upload";
        RequestParams params = new RequestParams();
        for (int i = 0; i < attendInfo.getphotoList().size(); i++) {
            try {
                params.put("upload"+i, new File(attendInfo.getphotoList().get(i)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        observableHelper.addObserver(observer);
        httpClient.post(targetUrl, params, new AsyncHttpResponseHandler() { //上传图片
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("uploadMessget", new String(responseBody));
                UploadMsg uploadMsg = parseUploadResult(responseBody);
                if (uploadMsg != null && uploadMsg.success) {       //上传成功
                    attendInfo.setphotoList(uploadMsg.fileNames);
                    submitAttendWithPhoto(attendInfo);     //提交考勤数据
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                observableHelper.setChanged();
                observableHelper.notifyObservers("上传图片失败");
                error.printStackTrace();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int cur = (int) (bytesWritten * 100 / totalSize);
                observableHelper.setChanged();
                observableHelper.notifyObservers(cur);
            }
        });
    }

    /*
    提交考勤数据
     */
    private static void submitAttendWithPhoto(AttendInfo attendInfo) {
        RequestParams requestParams = arrangeAttendData(attendInfo);
        String targetUrl = BASEURL + "attendance/create";

        httpClient.post(targetUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String s = new String(responseBody);
                    System.out.println(s);
                    JSONObject json = new JSONObject(s);
                    if (json.getBoolean("success")) {
                        observableHelper.setChanged();
                        observableHelper.notifyObservers(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                observableHelper.setChanged();
                observableHelper.notifyObservers("连接服务器失败");
                error.printStackTrace();
            }
        });
    }

    private static RequestParams arrangeAttendData(AttendInfo attendInfo){
        RequestParams params = new RequestParams();
        params.put("companyId",attendInfo.getCompanyId());
        params.put("employeeId",attendInfo.getEmployeeId());
        params.put("typeId",attendInfo.getTypeId());
        params.put("latitude",attendInfo.getLatitude());
        params.put("longitude",attendInfo.getLongitude());
        params.put("distance",attendInfo.getDistance());
        params.put("others", attendInfo.getOthers());
        params.put("flag",attendInfo.getAttendTag());
        String pics = attendInfo.getphotoList().toString();
        System.out.println(pics);
        params.put("pics", pics);
        params.put("time", attendInfo.getTime());
        return params;
    }

    private static UploadMsg parseUploadResult(byte[] json) {
        UploadMsg msg = null;
        try {
            msg = gson.fromJson(new String(json), UploadMsg.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return msg;
    }


    private static class ObservableHelper extends Observable{
        @Override
        protected void setChanged() {
            super.setChanged();
        }
    }

}
