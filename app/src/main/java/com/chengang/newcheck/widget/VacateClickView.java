package com.chengang.newcheck.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;

public class VacateClickView extends RelativeLayout {

	private TextView tvTitle;
	private TextView tvDate;
	private TextView tvTime;

	public TextView getTvDate() {
		return tvDate;
	}

	public TextView getTvTime() {
		return tvTime;
	}

	public VacateClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public VacateClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public VacateClickView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		View.inflate(getContext(), R.layout.view_vacate_click, this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvDate = (TextView) findViewById(R.id.tv_date);
		tvTime = (TextView) findViewById(R.id.tv_time);
	}
	public void setTitle(String title) {
		tvTitle.setText(title);
	}
	public void setDate(String date) {
		tvDate.setText(date);
	}
	public void setTime(String time) {
		tvTime.setText(time);
	}
}
