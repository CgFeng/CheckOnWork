package com.chengang.newcheck.widget;

import android.animation.Animator;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.adapter.AttendDetailAdapter;
import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.widget.basepopup.BasePopupWindow;

import java.util.List;

/**
 * Created by urcha on 2016/4/24.
 */
public class AttendPopup extends BasePopupWindow implements View.OnClickListener{
    private RecyclerView recyclerView;
    private AttendDetailAdapter adapter;
    private LinearLayoutManager manager;
    private List<AttendDetailInfo> data;
    private Activity activity;


    public AttendPopup(Activity context,List<AttendDetailInfo> data) {
        super(context);
        this.data = data;
        this.activity =context;
        initRecyclerView();
    }

    /**
     * RecyclerView填充数据
     */
    private void initRecyclerView() {
        recyclerView= (RecyclerView) mPopupView.findViewById(R.id.popup_attend_recyclerview);
        manager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        adapter=new AttendDetailAdapter(data,activity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    public Animator getShowAnimator() {
        return getDefaultSlideFromBottomAnimationSet();
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView.findViewById(R.id.popup_attend_root);
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_attend_detail);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.popup_attend_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }

    }

}
