package com.chengang.newcheck.ui;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.utils.BasePopup;
import com.chengang.newcheck.utils.CameraUtils;

/**
 * Created by Administrator on 2015/10/21.
 */
public class MyPopuWindow extends BasePopup {

    private View v;
    private Activity c;

    public MyPopuWindow(Activity activity) {
        super(activity);
        c=activity;
        v.findViewById(R.id.popup_choose_from_camera).setOnClickListener(this);
        v.findViewById(R.id.popup_choose_from_album).setOnClickListener(this);
    }

    @Override
    public void setTitleText() {
        ((TextView)v.findViewById(R.id.popup_base_title)).setText("请选择图片来源");
    }

    @Override
    public View getView() {
        v=inflateView(R.layout.popup_base);
        return v;
    }

    @Override
    public Animation setAnima() {
        return getScaleAnimation();
    }

    @Override
    public View getCancelButton() {
        return v.findViewById(R.id.popup_cancel);
    }

    @Override
    public View getCompleteButton() {
        return null;
    }

    //------------------------------------------点击事件---------------------------------------------

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.popup_choose_from_camera:
                //从相机读取
                CameraUtils.takePhotos(c);
                dismiss();
                break;
            case R.id.popup_choose_from_album:
                //从相册读取
                CameraUtils.takePhotoFromAlbum(c);
                dismiss();
                break;
        }

    }
}
