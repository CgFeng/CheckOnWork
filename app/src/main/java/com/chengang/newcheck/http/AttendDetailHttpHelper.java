package com.chengang.newcheck.http;

import android.util.Log;

import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.bean.AttendInfo;
import com.chengang.newcheck.bean.UploadMsg;
import com.chengang.newcheck.utils.DateUtil;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by urcha on 2015/04/24.
 */
public class AttendDetailHttpHelper extends BaseHttpHelper {
    private static final ObservableHelper observableHelper = new ObservableHelper();

    /**
     * 获取考勤信息
     * @param date
     * @param employeeId
     */
    public static void getAttendDetailById(String date, String employeeId ,final Observer observer) {
        String targetUrl = BASEURL + "attendance";
        RequestParams requestParams = new RequestParams();
        requestParams.put("date",date);
        requestParams.put("employeeId",employeeId);
        observableHelper.addObserver(observer);
        httpClient.get(targetUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        JSONObject jsonObject = null;
                        AttendDetailInfo a = null;
                        List<AttendDetailInfo> data = new ArrayList<AttendDetailInfo>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            a = new AttendDetailInfo();
                            List<String> photoList = new ArrayList<String>();//图片
                            jsonObject = jsonArray.getJSONObject(i);
                            JSONObject typeO = jsonObject.getJSONObject("type");

                            Date d = new Date(Long.valueOf(jsonObject.getString("time")));
                            a.setTime(DateUtil.date2String(d.getTime(), "yyyy-MM-dd hh:mm:ss"));
                            String pics = jsonObject.optString("pics");
                            pics = pics.replace("[", "").replace("]", "");
                            String[] arr = pics.split(",");//分割字符串得到数组
                            List list = java.util.Arrays.asList(arr);//字符数组转list
                            photoList.clear();
                            for (int j = 0; j < list.size(); j++) {
                                photoList.add("http://120.26.198.104/attendance" + (String) list.get(j));
                            }
                            a.setPhotoList(photoList);//考勤图片
                            a.setAttendType(typeO.optString("name"));//考勤类型名称，如：上班
                            a.setDistance(jsonObject.optString("distance"));//考勤距离
                            a.setAttendTag("正常");//考勤描述
                            data.add(a);
                        }
                        observableHelper.setChanged();
                        observableHelper.notifyObservers(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private static class ObservableHelper extends Observable{
        @Override
        protected void setChanged() {
            super.setChanged();
        }
    }

}
