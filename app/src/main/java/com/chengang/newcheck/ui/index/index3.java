package com.chengang.newcheck.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengang.drawerlayoutdemo.R;

/**
 * Created by 陈岗 on 2015/10/22.
 */
public class index3 extends Fragment{
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.index3,null);
        return rootView;
    }
}
