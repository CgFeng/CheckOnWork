package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.bean.VacateInfo;

import java.util.List;

/**
 * Created by 陈岗 on 2015/10/23.
 * 请假详情适配器
 */
public class VacateDetailAdapter extends BaseRecyclerViewAdapter<VacateInfo,VacateDetailAdapter.ViewHolder>{

    public VacateDetailAdapter(List<VacateInfo> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.item_vacate;
    }

    @Override
    protected ViewHolder initViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindData(ViewHolder holder, VacateInfo data, int position) {
        holder.tv_vacate_reason.setText(data.getReason());
        holder.tv_vacate_start.setText(data.getStart());
        holder.tv_vacate_end.setText(data.getEnd());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_vacate_reason;
        TextView tv_vacate_start;
        TextView tv_vacate_end;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_vacate_reason = (TextView) itemView.findViewById(R.id.tv_reason);
            tv_vacate_start = (TextView) itemView.findViewById(R.id.tv_start);
            tv_vacate_end = (TextView) itemView.findViewById(R.id.tv_end);

        }
    }
}


