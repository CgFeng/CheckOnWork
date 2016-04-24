package com.chengang.newcheck.ui.index;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.adapter.PhotoAdapter;
import com.chengang.newcheck.bean.AttendInfo;
import com.chengang.newcheck.bean.PhotoUploadTest;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.common.STATIC_INFO;
import com.chengang.newcheck.http.AttendHttpHelper;
import com.chengang.newcheck.ui.LoginActivity;
import com.chengang.newcheck.ui.fragmentMain.BaseFragment;
import com.chengang.newcheck.ui.fragmentMain.IndexFragment;
import com.chengang.newcheck.utils.CameraUtils;
import com.chengang.newcheck.utils.DateUtil;
import com.chengang.newcheck.utils.StringUtil;
import com.chengang.newcheck.utils.thread.ThreadPoolManager;
import com.chengang.newcheck.widget.SelectPopup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * 提交考勤
 * Created by 陈岗 on 2015/10/22.
 */
public class AttendFragment extends Fragment implements View.OnClickListener,AMapLocationListener,BaseFragment,Observer {
    private Thread clockThread;
    private boolean startClock = true;
    private View rootView;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSec;
    private TextView tv_check_time;
    private TextView tv_longitude;
    private TextView tv_latitude;
    private EditText etOthers;
    private ImageButton ibTakePhoto;
    private Double geoLat;
    private Double geoLng;
    private ProgressDialog pd;
    private Button btnSubmit;
    private ProgressDialog pbDialog;
    private LocationManagerProxy mLocationManagerProxy;

    private SelectPopup popup;
    private GridView gv_photo;
    private PhotoAdapter photoAdapter;
    private static final ArrayList<String> photoList = new ArrayList<String>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static File appRootFile;//照片的根目录
    public static Uri imageUri;//图片的uri

    WeakHandler handler;
    private Activity context;

    private String typeId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.activity_attend,null);
        context=getActivity();
        initView();
        initGridView();
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
//        clockThread = new Thread(new ClockThread());
//        clockThread.start();
        ThreadPoolManager.execute(new ClockThread());
        return rootView;
    }

    private void initView() {
        tvHour = (TextView) rootView.findViewById(R.id.tv_hour);
        tvMin = (TextView) rootView.findViewById(R.id.tv_min);
        tvSec = (TextView) rootView.findViewById(R.id.tv_sec);
        etOthers = (EditText) rootView.findViewById(R.id.et_ps);
        tv_longitude = (TextView) rootView.findViewById(R.id.tv_longitude);
        tv_latitude = (TextView) rootView.findViewById(R.id.tv_latitude);
        tv_check_time = (TextView) rootView.findViewById(R.id.tv_check_time);
        ibTakePhoto = (ImageButton) rootView.findViewById(R.id.ib_camera);
        gv_photo = (GridView) rootView.findViewById(R.id.gv_photo);
        btnSubmit = (Button) rootView.findViewById(R.id.btn_submit);
        popup=new SelectPopup(context);
    }

    private void initGridView() {
        photoAdapter = new PhotoAdapter(getContext(), photoList);
        gv_photo.setAdapter(photoAdapter);
    }

    private void initListener() {
        btnSubmit.setOnClickListener(this);
        tv_check_time.setOnClickListener(this);
        ibTakePhoto.setOnClickListener(this);
    }

    private void initProgressDialog() {
        pbDialog = new ProgressDialog(getContext());
        pbDialog.setTitle("正在提交");
        pbDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pbDialog.setMax(100);
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

    @Override
    public void onFragmentActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                getActivity();
                if (resultCode == Activity.RESULT_OK) {
                    photoList.add(CameraUtils.imageUri.getPath());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    Bitmap bitmap = BitmapFactory.decodeFile(CameraUtils.imageUri.getPath(), options);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG,80,new FileOutputStream(CameraUtils.imageUri.getPath()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    photoAdapter.notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i("222222222222222", data.toString());
        if(data instanceof String) {
            pbDialog.dismiss();
            pbDialog = null;
            StringUtil.myToast(getContext(), data.toString());
        }else{
            int cur = (int) data;
            pbDialog.setProgress(cur);
        }
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
            case R.id.ib_camera:
                CameraUtils.takePhoto(getActivity());
                break;
            case R.id.btn_submit:
                if (StringUtil.isEmpty(STATIC_INFO.EMPLOYEE_ID)){
                    Intent toLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(toLogin);
                    return;
                }
                if (photoList.size()<=0){
                    StringUtil.myToast(getActivity(),"亲，至少拍一张照片");
                    return;
                }
                if(pbDialog==null){
                    initProgressDialog();
                }
                pbDialog.show();
                AttendHttpHelper.submit(arrangeAttendInfo(),this);
                break;
        }
    }

    private AttendInfo arrangeAttendInfo() {
        AttendInfo attendInfo = null;
        //计算考勤的状态
        String attendTag = null;
        if (typeId.equals("1")){
            //上班
            String attendTime = DateUtil.date2String(System.currentTimeMillis(),"yyyy-MM-dd")+" 09:00:00";
            if (System.currentTimeMillis()<=DateUtil.string2Date(attendTime, "yyyy-MM-dd HH:mm:ss").getTime()){
                attendTag = DICT.ATTEND_NORMAL;
            }else{
                attendTag = DICT.ATTEND_LATE;
            }
        }else{
            //下班
            String attendTime = DateUtil.date2String(System.currentTimeMillis(),"yyyy-MM-dd")+" 18:00:00";
            if (System.currentTimeMillis()>=DateUtil.string2Date(attendTime, "yyyy-MM-dd HH:mm:ss").getTime()){
                attendTag = DICT.ATTEND_NORMAL;
            }else{
                attendTag = DICT.ATTEND_ZAOTUI;
            }
        }

        //计算距离
        int distance = (int) AMapUtils.calculateLineDistance(
                new LatLng(geoLat, geoLng),
                new LatLng(Double.valueOf(STATIC_INFO.COMPANY_LATITUDE), Double.valueOf(STATIC_INFO.COMPANY_LONGITUDE)));
        attendInfo = new AttendInfo(STATIC_INFO.COMPANY_ID,
                                    STATIC_INFO.EMPLOYEE_ID,
                                    typeId,
                                    String.valueOf(geoLat),
                                    String.valueOf(geoLng),
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                                    String.valueOf(distance),
                                    etOthers.getText().toString(),
                                    attendTag,
                                    photoList);
        return attendInfo;
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
                   if (v instanceof TextView) {
                       String text = ((TextView) v).getText().toString().trim();
                       tv_check_time.setText(text);
                       typeId = "1";//上班
                   }
               }

               @Override
               public void onHomeClick(View v) {
                   if (v instanceof TextView) {
                       String text = ((TextView) v).getText().toString().trim();
                       tv_check_time.setText(text);
                       typeId = "2";//下班
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

    /**
     * 校验是否可以提交
     */
    private void validateSubmit(){
        if (StringUtil.isEmpty(STATIC_INFO.EMPLOYEE_ID)){
            Intent toLogin = new Intent(getActivity(), LoginActivity.class);
            startActivity(toLogin);
            return;
        }
        if (photoList.size()<=0){
            StringUtil.myToast(getActivity(),"亲，至少拍一张照片");
            return;
        }
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




    /*-------------------------下面的方法不常用---------------------*/
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
}
