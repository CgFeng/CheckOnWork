package com.chengang.newcheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.chengang.newcheck.utils.ScreenUtils;

import java.util.List;

/**
 * recyclerView基类
 * Created by fengchengang on 2016/4/17.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
        implements View.OnClickListener{

    private int ANIMATED_ITEMS_COUNT=6;
    private int lastAnimatedPosition=-1;

    protected List<T> datas;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public BaseRecyclerViewAdapter(List<T> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
        mLayoutInflater =  LayoutInflater.from(mContext);
    }

    protected abstract int getViewResId();
    protected abstract VH initViewHolder(View view);
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getViewResId(), parent, false);
        view.setOnClickListener(this);
        return initViewHolder(view);
    }

    protected abstract void onBindData(VH holder, T data, int position);
    @Override
    public void onBindViewHolder(VH holder, int position) {
        T data = datas.get(position);
        holder.itemView.setTag(data);
        runEnterAnimation(holder.itemView,position);
        onBindData(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }






    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onRecyclerViewItemClick(v, v.getTag());
        }
    }

    /**
     * item进入时候的动画
     */
    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(ScreenUtils.getScreenHeight(mContext));//把item移出屏幕
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(800)
                    .start();
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
