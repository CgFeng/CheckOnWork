package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.bean.Notification;

import java.util.List;

/**
 * Created by 陈岗 on 2015/10/23.
 * 公告适配器
 */
public class AttendDetailAdapter extends BaseRecyclerViewAdapter<AttendDetailInfo,AttendDetailAdapter.ViewHolder>{

    public AttendDetailAdapter(List<AttendDetailInfo> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.item_attend_detail;
    }

    @Override
    protected ViewHolder initViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindData(ViewHolder holder, AttendDetailInfo data, int position) {
        holder.tv_attend_time.setText(data.getTime());
        holder.tv_attend_category.setText(data.getAttendType());
        holder.tv_attend_desc.setText(data.getAttendTag());
        holder.tv_attend_distance.setText(data.getDistance());
        holder.tv_attend_others.setText(data.getOthers());
        holder.gv_attend_photo.setAdapter(new PhotoAdapter(mContext, data.getPhotoList()));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_attend_time;//考勤日期
        TextView tv_attend_category;//考勤类别
        TextView tv_attend_desc;//考勤描述
        TextView tv_attend_distance;//位置距离
        TextView tv_attend_others;//考勤备注
        GridView gv_attend_photo;//考勤照片

        public ViewHolder(View itemView) {
            super(itemView);
            tv_attend_time = (TextView) itemView.findViewById(R.id.tv_attend_time);
            tv_attend_category = (TextView) itemView.findViewById(R.id.tv_attend_category);
            tv_attend_desc = (TextView) itemView.findViewById(R.id.tv_attend_desc);
            tv_attend_distance = (TextView) itemView.findViewById(R.id.tv_attend_distance);
            tv_attend_others = (TextView) itemView.findViewById(R.id.tv_attend_others);
            gv_attend_photo = (GridView) itemView.findViewById(R.id.gv_attend_photo);

        }
    }
}


