package com.chengang.newcheck.http;

import android.content.Context;

import com.chengang.newcheck.BaseApplication;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by fengchengang on 2015/11/16.
 */
public class BaseHttpHelper{
    //192.168.4.55
    protected static final String  BASEURL= "http://192.168.1.106:8080/tocean_attendance/mobile/";
    protected static final AsyncHttpClient httpClient = new AsyncHttpClient();
    protected static final Gson gson = new Gson();
    protected static final Context context = BaseApplication.appContext;

    protected static void saveCache(String key,String value){
        context.getSharedPreferences("cache", Context.MODE_PRIVATE)
                .edit()
                .putString(key,value)
                .commit();
    }

    protected static String readCache(String key){
        return context.getSharedPreferences("cache",Context.MODE_PRIVATE).getString(key,"");
    }

}
