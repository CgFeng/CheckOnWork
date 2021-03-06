package com.chengang.newcheck.ui.fragmentMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.adapter.MyPagerAdapter;
import com.chengang.newcheck.ui.index.AttendFragment;
import com.chengang.newcheck.ui.index.index2;
import com.chengang.newcheck.ui.index.index3;

/**
 * Created by 陈岗 on 2015/10/22.
 */
public class IndexFragment extends Fragment implements BaseFragment{

    private View rootView;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private AttendFragment mAttendActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAttendActivity=new AttendFragment();
        rootView=inflater.inflate(R.layout.fragment_index,null);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.slide_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("内容简介"));
        tabLayout.addTab(tabLayout.newTab().setText("作者简介"));
        tabLayout.addTab(tabLayout.newTab().setText("目录"));
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }


    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(mAttendActivity, "每日考勤");
        adapter.addFragment(new index2(), "请休假");
        adapter.addFragment(new index3(), "考勤提醒");
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentActivityResult(int requestCode, int resultCode, Intent data) {
        mAttendActivity.onFragmentActivityResult(requestCode,resultCode,data);

    }
}
