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

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.adapter.BaseRecyclerViewAdapter;
import com.chengang.newcheck.adapter.NotificationAdapter;
import com.chengang.newcheck.adapter.VacateDetailAdapter;
import com.chengang.newcheck.bean.Notification;
import com.chengang.newcheck.bean.VacateInfo;
import com.chengang.newcheck.http.NoticeHttpHelper;
import com.chengang.newcheck.ui.NoticeDetailActivity;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by 陈岗 on 2016/4/25.
 */
public class FourthFragment extends Fragment implements BaseFragment,Observer,BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener {

    private View rootView;

    private RecyclerView mRecyclerView;
    private VacateDetailAdapter vacateDetailAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MaterialRefreshLayout materialRefreshLayout;
    private List<VacateInfo> vacateList = new ArrayList<VacateInfo>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_fourth,null);
        initView();
        vacateDetailAdapter = new VacateDetailAdapter(vacateList, getActivity());
        vacateDetailAdapter.setonRecyclerViewItemClickListener(this);
        mRecyclerView.setAdapter(vacateDetailAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        materialRefreshLayout.autoRefresh();


        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
//                NoticeHttpHelper.getNoticeData(NoticeFragment.this);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
            }
        });
        return rootView;
    }

    private void initView() {
        materialRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof List) {
            vacateList.clear();
            vacateList.addAll((Collection<? extends VacateInfo>) data);
            vacateDetailAdapter.notifyDataSetChanged();
            materialRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onRecyclerViewItemClick(View v, Object data) {

    }

    @Override
    public void onFragmentActivityResult(int requestCode, int resultCode, Intent data) {
        Intent toDetail = new Intent(getContext(),NoticeDetailActivity.class);
//        Notification notification = (Notification) data;
//        toDetail.putExtra("noticeDetail", notification);
        startActivity(toDetail);
    }
}
