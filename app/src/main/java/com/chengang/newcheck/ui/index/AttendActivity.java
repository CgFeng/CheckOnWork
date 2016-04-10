package com.chengang.newcheck.ui.index;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.widget.SelectPopup;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 提交考勤
 * Created by 陈岗 on 2015/10/22.
 */
public class AttendActivity extends Fragment implements View.OnClickListener,AMapLocationListener {
    private Thread clockThread;
    private boolean startClock = true;
    private View rootView;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSec;
    private TextView tv_check_time;
    private TextView tv_longitude;
    private TextView tv_latitude;
    private Double geoLat;
    private Double geoLng;
    private ProgressDialog pd;
    private LocationManagerProxy mLocationManagerProxy;

    private SelectPopup popup;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    WeakHandler handler;
    private Activity context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.activity_attend,null);
        context=getActivity();
        initView();
        initListener();
        handler=new WeakHandler(context){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        updateTime((String) msg.obj);
                        break;
                }
            }
        };

        startClock = true;
        clockThread = new Thread(new ClockThread());
        clockThread.start();
        return rootView;
    }

    private void initView() {
        tvHour = (TextView) rootView.findViewById(R.id.tv_hour);
        tvMin = (TextView) rootView.findViewById(R.id.tv_min);
        tvSec = (TextView) rootView.findViewById(R.id.tv_sec);
        tv_longitude = (TextView) rootView.findViewById(R.id.tv_longitude);
        tv_latitude = (TextView) rootView.findViewById(R.id.tv_latitude);
        tv_check_time = (TextView) rootView.findViewById(R.id.tv_check_time);
        popup=new SelectPopup(context);
    }

    private void initListener() {
        tv_check_time.setOnClickListener(this);
    }

    /*
     * 初始化定位
     */
    private void initLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(getContext());
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
        mLocationManagerProxy.setGpsEnable(false);
    }

    /*
    * 开始定位
    */
    private void locate() {
        if (pd == null) {
            pd = new ProgressDialog(getContext());
            pd.setMessage("正在获取位置");
        }
        pd.show();
        initLocation();
    }

    /*
     * 更新时间
     */
    private void updateTime(String time) {
        String[] timeStr = time.split(":");
        tvHour.setText(timeStr[0]);
        tvMin.setText(timeStr[1]);
        tvSec.setText(timeStr[2]);

    }

    /**
     * 时钟线程
     */
    class ClockThread implements Runnable {
        @Override
        public void run() {
            while (startClock) {
                String time = sdf.format(new Date());
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = time;
                handler.sendMessage(message);
                SystemClock.sleep(1000);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check_time:
                showCheckDialog();
                break;
        }
    }

    @Override
    public void onPause() {
        stopLocation();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacksAndMessages(null);
        startClock = false;
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        locate();
        super.onActivityCreated(savedInstanceState);
    }

    /*
     * 考勤类型选择
     */
    private void showCheckDialog() {
       if (popup.getOnPopupItemClickListenr()==null){
           popup.setOnPopupItemClickListenr(new SelectPopup.OnPopupItemClickListenr() {
               @Override
               public void onWorkClick(View v) {
                   if (v instanceof TextView){
                       String text= ((TextView) v).getText().toString().trim();
                       tv_check_time.setText(text);

                   }
               }

               @Override
               public void onHomeClick(View v) {
                   if (v instanceof TextView){
                       String text= ((TextView) v).getText().toString().trim();
                       tv_check_time.setText(text);

                   }
               }
           });
       }
        popup.showPopupWindow();


    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0){
            //获取位置信息
            geoLat = aMapLocation.getLatitude();
            geoLng = aMapLocation.getLongitude();
            tv_latitude.setText("纬度：" + geoLat);
            tv_longitude.setText("经度：" + geoLng);
            pd.dismiss();
        }
    }

    /*
     * 移除定位请求,并对定位服务对象进行销毁
     */
    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    static class WeakHandler extends Handler{
        private final WeakReference<Context> context;

        public WeakHandler(Context context) {
            this.context=new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
