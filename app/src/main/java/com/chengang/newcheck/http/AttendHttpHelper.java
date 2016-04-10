package com.chengang.newcheck.http;

import java.util.Observable;

/**
 * Created by Administrator on 2015/11/16.
 */
public class AttendHttpHelper extends BaseHttpHelper {
    private static final ObservableHelper observableHelper = new ObservableHelper();

    private static class ObservableHelper extends Observable{
        @Override
        protected void setChanged() {
            super.setChanged();
        }
    }

}
