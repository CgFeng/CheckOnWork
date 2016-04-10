package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Notification;

import java.util.List;

/**
 * Created by 陈岗 on 2015/10/23.
 * 公告适配器
 */
public class NotificationAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Notification> NoticeList;

    public NotificationAdapter(Context context,List<Notification> NoticeList) {
        this.context=context;
        this.NoticeList=NoticeList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_second_fragment,parent,false);
        MyViewHolder myViewHolder =  new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.tvHead.setText(NoticeList.get(position).getHead());
        holder.tvSubhead.setText(NoticeList.get(position).getSubhead());
        holder.tvCondition.setText(NoticeList.get(position).getCondition());
        holder.tvAddTime.setText(NoticeList.get(position).getAddTime());
    }

    @Override
    public int getItemCount() {
        return NoticeList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView tvHead;
    TextView tvSubhead;
    TextView tvCondition;
    TextView tvAddTime;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.iv_item);
        tvHead= (TextView) itemView.findViewById(R.id.tv_head);
        tvSubhead= (TextView) itemView.findViewById(R.id.tv_subhead);
        tvCondition= (TextView) itemView.findViewById(R.id.tv_bottom_left);
        tvAddTime= (TextView) itemView.findViewById(R.id.tv_bottom_right);
    }
}
