package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Diary;
import com.chengang.newcheck.bean.Notification;

import java.util.List;

/**
 * Created by 陈岗 on 2016/4/25.
 * 记事本适配器
 */
public class DiaryAdapter extends BaseRecyclerViewAdapter<Diary,DiaryAdapter.ViewHolder>{

    public DiaryAdapter(List<Diary> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.item_diary;
    }

    @Override
    protected ViewHolder initViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindData(ViewHolder holder, Diary data, int position) {
        holder.tvDiaryCreateTime.setText(data.getCreateTime());
        holder.tvDiaryContent.setText(data.getContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDiaryCreateTime;
        TextView tvDiaryContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDiaryCreateTime= (TextView) itemView.findViewById(R.id.tv_diary_create_time);
            tvDiaryContent= (TextView) itemView.findViewById(R.id.tv_diary_content);
        }
    }
}


