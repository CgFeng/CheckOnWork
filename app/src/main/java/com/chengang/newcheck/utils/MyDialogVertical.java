package com.chengang.newcheck.utils;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.chengang.drawerlayoutdemo.R;

/**
 * Created by 陈岗 on 2015/11/4.
 */
public class MyDialogVertical extends Dialog implements View.OnClickListener {
    private Context context;
    private int layout_id;
    private OnDialogVerticalListner onDialogVerticalListner;


    public MyDialogVertical(Context context, int layout_id) {
        super(context);
        this.context = context;
        this.layout_id = layout_id;
        initDialog();
    }

    private void initDialog() {
        initDialogStyle();
        View view = LayoutInflater.from(context).inflate(layout_id, null);
        setContentView(view);
        view.setBackgroundResource(R.drawable.vifrification);
        setCanceledOnTouchOutside(true);

        //view.setAnimation( AnimationUtils.loadAnimation(context, R.anim.dialog_enter_anim));
        RelativeLayout re1 = (RelativeLayout) view.findViewById(R.id.re1);
        RelativeLayout re2 = (RelativeLayout) view.findViewById(R.id.re2);

        re1.setOnClickListener(this);
        re2.setOnClickListener(this);

    }

    /**
     * 给dialog设置样式
     */
    private void initDialogStyle() {
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.drawable.vifrification); //设置对话框背景为透明
        //上面的代码，是用来去除Holo主题的蓝色线条
        try {
            int dividerID = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LayoutParams para = window.getAttributes();
//        LayoutParams para = new LayoutParams();
        para.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        para.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(para);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re1:
                onDialogVerticalListner.OnRe1Click(view);
                break;

            case R.id.re2:
                onDialogVerticalListner.OnRe2Click(view);
                break;
        }
    }


    //-----------------------------------接口---------------------------------
    public interface OnDialogVerticalListner {
        void OnRe1Click(View view);

        void OnRe2Click(View view);
    }

    public void setOnDialogVerticalListner(OnDialogVerticalListner onDialogVerticalListner) {
        this.onDialogVerticalListner = onDialogVerticalListner;
    }
}
