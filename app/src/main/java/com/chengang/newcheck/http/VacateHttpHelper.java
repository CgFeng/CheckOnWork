package com.chengang.newcheck.http;

import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.bean.Login;
import com.chengang.newcheck.bean.VacateInfo;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.common.STATIC_INFO;
import com.chengang.newcheck.utils.DateUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by fengchengang on 2016/3/20.
 */
public class VacateHttpHelper extends BaseHttpHelper {

    private static final ObservableHelper observableHelper = new ObservableHelper();

    /**
     * 提交请假
     * @param start
     * @param end
     * @param reason
     * @param observer
     */
    public static void submitVacate(String start,String end,String reason, final Observer observer) {
        String targetUrl = BASEURL + "vacate/create";

        RequestParams params = new RequestParams();
        params.put("companyId", STATIC_INFO.COMPANY_ID);
        params.put("employeeId", STATIC_INFO.EMPLOYEE_ID);
        params.put("typeId", "1");
        params.put("managerId", "2");
        params.put("start", start);
        params.put("end", end);
        params.put("reason", reason);

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
                            observableHelper.notifyObservers("TRUE");
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

    /**
     * 获取请假信息
     * @param observer
     */
    public static void getVacateDetail(final Observer observer) {
        String targetUrl = BASEURL + "vacate";
        RequestParams requestParams = new RequestParams();
        requestParams.put("employeeId",STATIC_INFO.EMPLOYEE_ID);
        requestParams.put("status","-1");
        observableHelper.addObserver(observer);
        httpClient.get(targetUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        JSONObject jsonObject = null;
                        VacateInfo vacateInfo = null;
                        List<VacateInfo> data = new ArrayList<VacateInfo>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            vacateInfo = new VacateInfo();
                            vacateInfo.setReason(jsonObject.optString("reason"));
                            Date startTime = new Date(jsonObject.optString("start"));
                            vacateInfo.setStart(DateUtil.date2String(startTime.getTime(), "yy-MM-dd hh:mm:ss"));
                            Date endTime = new Date(jsonObject.optString("end"));
                            vacateInfo.setEnd(DateUtil.date2String(endTime.getTime(),"yy-MM-dd hh:mm:ss"));
                            data.add(vacateInfo);
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
