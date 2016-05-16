package com.chengang.newcheck.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Notification;
import com.chengang.newcheck.ui.NoticeDetailActivity;
import com.chengang.newcheck.utils.ScreenUtils;

import java.util.List;

/**
 * Created by 陈岗 on 2015/10/23.
 * 公告适配器
 */
public class NotificationAdapter extends BaseRecyclerViewAdapter<Notification,NotificationAdapter.ViewHolder>{

    public NotificationAdapter(List<Notification> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.item_second_fragment;
    }

    @Override
    protected ViewHolder initViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindData(ViewHolder holder, Notification data, int position) {
        holder.tvHead.setText(data.getTitle());
        holder.tvCondition.setText(data.getContent());
        holder.tvAddTime.setText(data.getPublishTime());
        Glide.with(mContext)
                .load("http://imglf2.nosdn.127.net/img/NWxuTTNsdXVnVlBMaG1zam5uRkc2OW1Ic09wRjRvQ3pQamZFSGZIdGZwWnkyWmJEZmxRcHlBPT0.jpg?imageView&thumbnail=2000y2000&type=jpg&quality=96&stripmeta=0&type=jpg%7Cwatermark&type=2&text=wqkg6ZmI5bKX5LiN5aeT6ZmIIC8gY2hhbmtvbmcubG9mdGVyLmNvbQ==&font=bXN5aA==&gravity=southwest&dissolve=30&fontsize=680&dx=32&dy=36&stripmeta=0")
                .into(holder.imageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvHead;
        TextView tvSubhead;
        TextView tvCondition;
        TextView tvAddTime;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);
            tvHead= (TextView) itemView.findViewById(R.id.tv_head);
            tvSubhead= (TextView) itemView.findViewById(R.id.tv_subhead);
            tvCondition= (TextView) itemView.findViewById(R.id.tv_bottom_left);
            tvAddTime= (TextView) itemView.findViewById(R.id.tv_bottom_right);
        }
    }
}


