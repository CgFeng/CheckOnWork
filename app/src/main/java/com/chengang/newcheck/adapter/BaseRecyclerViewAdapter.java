package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * recyclerView基类
 * Created by urcha on 2016/4/17.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
        implements View.OnClickListener{

    protected List<T> datas;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public BaseRecyclerViewAdapter(List<T> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
        mLayoutInflater =  LayoutInflater.from(mContext);;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getViewResId(), parent, false);
        view.setOnClickListener(this);
        return initViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        T data = datas.get(position);
        holder.itemView.setTag(data);
        onBindData(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected abstract int getViewResId();

    protected abstract VH initViewHolder(View view);

    protected abstract void onBindData(VH holder, T data, int position);

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onRecyclerViewItemClick(v, v.getTag());
        }
    }


    /*-----------------点击事件接口---------------------*/

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(View v, Object data);
    }

    public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }

    public void setonRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

}
