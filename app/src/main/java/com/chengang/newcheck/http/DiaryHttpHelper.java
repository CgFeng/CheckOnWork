package com.chengang.newcheck.http;

import android.util.Log;

import com.chengang.newcheck.bean.AttendInfo;
import com.chengang.newcheck.bean.UploadMsg;
import com.chengang.newcheck.common.STATIC_INFO;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Feng Chengang on 2016/4/25.
 */
public class DiaryHttpHelper extends BaseHttpHelper {
    private static final ObservableHelper observableHelper = new ObservableHelper();

    public static void markDownDiary(String content, final Observer observer) {
        String targetUrl = BASEURL + "message/create";
        RequestParams params = new RequestParams();
        params.put("companyId", STATIC_INFO.COMPANY_ID);
        params.put("employeeId", STATIC_INFO.EMPLOYEE_ID);
        params.put("content", content);
        observableHelper.addObserver(observer);
        httpClient.post(targetUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                observableHelper.setChanged();
                observableHelper.notifyObservers("上传失败");
                error.printStackTrace();
            }

        });
    }

    private static class ObservableHelper extends Observable {
        @Override
        protected void setChanged() {
            super.setChanged();
        }
    }
}
