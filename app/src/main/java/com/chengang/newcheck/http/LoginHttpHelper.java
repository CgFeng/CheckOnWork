package com.chengang.newcheck.http;

import com.chengang.newcheck.bean.Login;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.common.STATIC_INFO;
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
     *
     * @param login    登录信息
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
                        JSONObject userInfo = new JSONObject(s);
                        if (userInfo.getBoolean("success")) {
                            observableHelper.setChanged();
                            markDownUserInfo(userInfo);
                            observableHelper.notifyObservers(DICT.LOGIN_SUCCESS);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            /**
             * 记录登录后员工的信息
             */
            private void markDownUserInfo(JSONObject userInfo) {
                STATIC_INFO.COMPANY_ID = userInfo.optString("companyId");
                STATIC_INFO.COMPANY_NAME = userInfo.optString("companyName");
                STATIC_INFO.DEPARTMENT_NAME = userInfo.optString("departmentName");
                STATIC_INFO.EMPLOYEE_ID = userInfo.optString("employeeId");
                STATIC_INFO.COMPANY_NAME = userInfo.optString("employeeName");
                STATIC_INFO.EMPLOYEE_MOBILE = userInfo.optString("employeeMobile");
                STATIC_INFO.COMPANY_LATITUDE = userInfo.optString("companyLatitude");
                STATIC_INFO.COMPANY_LONGITUDE = userInfo.optString("companyLongitude");
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
