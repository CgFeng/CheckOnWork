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
        holder.tvSubhead.setText(data.getContent());
        holder.tvAddTime.setText(data.getPublishTime());
        Glide.with(mContext)
                .load("http://cdnq.duitang.com/uploads/item/201403/24/20140324145037_jcQ8Z.thumb.700_0.jpeg")
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


