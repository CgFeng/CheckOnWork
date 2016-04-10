package com.chengang.newcheck.ui.fragmentMain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengang.newcheck.adapter.NotificationAdapter;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈岗 on 2015/10/22.
 */
public class ThirdFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NotificationAdapter notificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Notification> noticeList=new ArrayList<Notification>();


    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_third,null);
        initView();
        initData();
        notificationAdapter=new NotificationAdapter(getContext(),noticeList);
        mRecyclerView.setAdapter(notificationAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    private void initView(){
        mRecyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    }

    private void initData() {
        Notification n = null;
        for(int i=0;i<20;i++){
            n= new Notification();
            n.setHead("2015 DVD "+i);
            n.setSubhead("D:\\Program Files (x86)\\Common Files\\Apple\\Internet Services\\APLZOD.resources\\ca.lproj");
            n.setCondition("204KB");
            n.setAddTime("3 days ago");
            noticeList.add(n);
        }
    }
}
