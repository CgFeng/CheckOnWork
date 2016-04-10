package com.chengang.newcheck;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/3/22.
 */
public class BaseApplication extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
