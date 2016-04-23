package com.chengang.newcheck.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Notification;

public class NoticeDetailActivity extends AppCompatActivity {
    private TextView tvNoticeTitle;
    private TextView tvNoticeCreateTime;
    private TextView tvNoticeContent;
    private Toolbar noticeToolbar;
    private Notification notification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        notification = (Notification) this.getIntent().getSerializableExtra("noticeDetail");
        initView();
        //返回按钮
        noticeToolbar.setTitle("公告详情");
        setSupportActionBar(noticeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noticeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        updateView(notification);
    }

    private void initView() {
        tvNoticeTitle = (TextView) findViewById(R.id.tvNoticeTitle);
        tvNoticeCreateTime = (TextView) findViewById(R.id.tvNoticeTime);
        tvNoticeContent = (TextView) findViewById(R.id.tvNoticeContent);
        noticeToolbar = (Toolbar) findViewById(R.id.noticeToolbar);
    }

    private void updateView(Notification notification) {
        tvNoticeTitle.setText(notification.getTitle());
        tvNoticeContent.setText(notification.getContent());
        tvNoticeCreateTime.setText(notification.getPublishTime());
    }

}
