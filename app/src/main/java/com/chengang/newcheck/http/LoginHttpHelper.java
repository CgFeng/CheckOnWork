package com.chengang.newcheck.http;

import com.chengang.newcheck.bean.Login;
import com.chengang.newcheck.common.DICT;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by fengchengang on 2016/3/20.
 */
public class LoginHttpHelper extends BaseHttpHelper {

    private static final ObservableHelper observableHelper = new ObservableHelper();

    /**
     * 登录
     * @param login 登录信息
     * @param observer
     */
    public static void submitLogin(Login login, final Observer observer) {

        String targetUrl = BASEURL + "employee/login";//登录url

        RequestParams params = new RequestParams();
        params.put("mobile", login.getAccount());
        params.put("password", login.getPassword());

        observableHelper.addObserver(observer);
        httpClient.post(targetUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String s = new String(responseBody);
                        System.out.println(s);
                        JSONObject json = new JSONObject(s);
                        if (json.getBoolean("success")) {
                            observableHelper.setChanged();
                            observableHelper.notifyObservers(DICT.LOGIN_SUCCESS);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                observableHelper.setChanged();
                observableHelper.notifyObservers(DICT.LOGIN_FAILURE);
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
