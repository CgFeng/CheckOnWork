package com.chengang.newcheck.ui.fragmentMain;

import android.content.Intent;
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
import com.chengang.newcheck.http.NoticeHttpHelper;
import com.cjj.MaterialHeadListener;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 公告通知
 * Created by 陈岗 on 2015/10/22.
 */
public class NoticeFragment extends Fragment implements BaseFragment,Observer{

    private RecyclerView mRecyclerView;
    private NotificationAdapter notificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MaterialRefreshLayout materialRefreshLayout;
    private List<Notification> noticeList=new ArrayList<Notification>();



    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_third,null);
        initView();
        notificationAdapter=new NotificationAdapter(noticeList,getActivity());
        mRecyclerView.setAdapter(notificationAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NoticeHttpHelper.getNoticeData(NoticeFragment.this);

        materialRefreshLayout.autoRefresh();



        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                NoticeHttpHelper.getNoticeData(NoticeFragment.this);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
            }
        });
        return rootView;
    }

    private void initView(){
        materialRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.refresh);
        mRecyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    public void onFragmentActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof List){
            noticeList.clear();
            noticeList.addAll((Collection<? extends Notification>) data);
            notificationAdapter.notifyDataSetChanged();
            materialRefreshLayout.finishRefresh();
        }
    }
}
