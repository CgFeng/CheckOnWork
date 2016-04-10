package com.chengang.newcheck.widget;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.widget.basepopup.BasePopupWindow;

/**
 * Created by urcha on 2016/4/10.
 */
public class SelectPopup extends BasePopupWindow implements View.OnClickListener{
    private TextView work;
    private TextView home;


    public SelectPopup(Activity context) {
        super(context);

        work= (TextView) mPopupView.findViewById(R.id.go_to_work);
        home= (TextView) mPopupView.findViewById(R.id.go_home);

        work.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    public Animator getShowAnimator() {
        return getDefaultSlideFromBottomAnimationSet();
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView.findViewById(R.id.popup_root);
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_selected);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.popup_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_home:
                if (onItemClickListenr!=null){
                    onItemClickListenr.onHomeClick(v);
                }
                dismiss();
                break;
            case R.id.go_to_work:
                onItemClickListenr.onWorkClick(v);
                dismiss();
                break;
            default:
                break;
        }

    }


    public OnPopupItemClickListenr getOnPopupItemClickListenr() {
        return onItemClickListenr;
    }

    public void setOnPopupItemClickListenr(OnPopupItemClickListenr onItemClickListenr) {
        this.onItemClickListenr = onItemClickListenr;
    }

    private OnPopupItemClickListenr onItemClickListenr;
    public interface OnPopupItemClickListenr{
        void onWorkClick(View v);
        void onHomeClick(View v);
    }
}
