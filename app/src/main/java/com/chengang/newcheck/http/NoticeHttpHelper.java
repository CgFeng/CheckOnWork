package com.chengang.newcheck.http;

import com.chengang.newcheck.bean.Login;
import com.chengang.newcheck.bean.Notification;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.common.STATIC_INFO;
import com.chengang.newcheck.utils.DateUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by urcha on 2016/4/17.
 */
public class NoticeHttpHelper extends BaseHttpHelper{
    private static final ObservableHelper observableHelper = new ObservableHelper();


    /**
     * 登录
     *
     * @param observer
     */
    public static void getNoticeData(final Observer observer) {

        String targetUrl = BASEURL + "notice";

        RequestParams params = new RequestParams();
        params.put("companyId", "3");

        observableHelper.addObserver(observer);
        httpClient.get(targetUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        List<Notification> notificationList = new ArrayList<Notification>();
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        JSONObject jsonObject=null;
                        Notification notification=null;
                        for(int i =0;i<jsonArray.length();i++){
                            notification = new Notification();
                            jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.optString("title");
                            String content = jsonObject.optString("content");
                            String publishTime = jsonObject.optString("publishTime");
                            notification.setTitle(title);
                            notification.setContent(content);
                            notification.setPublishTime(DateUtil.date2String(Long.valueOf(publishTime),"yyyy-MM-dd"));
                            notificationList.add(notification);
                        }
                        if (notificationList.size()>0){
                            observableHelper.setChanged();
                            observableHelper.notifyObservers(notificationList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                observableHelper.setChanged();
                observableHelper.notifyObservers("获取公共失败");
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
