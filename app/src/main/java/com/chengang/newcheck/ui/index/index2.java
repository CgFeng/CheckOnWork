package com.chengang.newcheck.ui.index;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.widget.VacateClickView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 陈岗 on 2015/10/22.
 */
public class index2 extends Fragment implements View.OnClickListener{
    private View rootView;
    private Button sumbit;
    private EditText etContent;
    private VacateClickView vacateType;        // 请假类型
    private VacateClickView leader;            //审批人
    private VacateClickView vacateStartTime;   //请假开始时间
    private VacateClickView vacateEndTime;     //请假结束时间

    private SharedPreferences mPref;
    // 用来保存年月日：小时，分钟
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_vacate, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        mPref = getContext().getSharedPreferences("config", getContext().MODE_PRIVATE);
        initView();
        initClass();
        initLeader();
        initStartTime();
        initStopTime();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        etContent.setText("");
    }

    private void initView() {
        etContent = (EditText) rootView.findViewById(R.id.et_content);
        sumbit = (Button) rootView.findViewById(R.id.sumbit);
        vacateType = (VacateClickView) rootView.findViewById(R.id.lcv_class_style);
        leader = (VacateClickView) rootView.findViewById(R.id.lcv_leader);
        vacateEndTime = (VacateClickView) rootView.findViewById(R.id.lcv_stopTime);
        vacateStartTime = (VacateClickView) rootView.findViewById(R.id.lcv_startTime);
        sumbit.setOnClickListener(this);
    }

    /**
     * 请假结束时间
     */
    // 时间格式
    public String timeFormat(int value) {
        return value >= 10 ? "" + value : "0" + value;
    }

    private void initStopTime() {
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        mHour = currentDate.get(Calendar.HOUR);
        mMinute = currentDate.get(Calendar.MINUTE);
        vacateEndTime.setTitle("结束时间:");
        vacateEndTime.setTime("选择结束时间");
        vacateEndTime.setDate("");
        vacateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker v, int hour,
                                                  int minute) {
                                vacateEndTime.setTime(String.format("%s:%s",
                                        timeFormat(hour), timeFormat(minute)));
                            }
                        }, mHour, mMinute, true).show();

                new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                vacateEndTime.setDate(String.format("%d-%s-%s", year,
                                        timeFormat(month + 1), timeFormat(day)));
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });
    }

    /**
     * 请假开始时间
     */
    private void initStartTime() {
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        mHour = currentDate.get(Calendar.HOUR);
        mMinute = currentDate.get(Calendar.MINUTE);
        initView();
        vacateStartTime.setTitle("开始时间:");
        vacateStartTime.setTime("选择开始时间");
        vacateStartTime.setDate("");
        vacateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker v, int hour,
                                                  int minute) {
                                vacateStartTime.setTime(String.format("%s:%s",
                                        timeFormat(hour), timeFormat(minute)));
                                // System.out.println(timeFormat(hour)+timeFormat(minute));
                            }
                        }, mHour, mMinute, true).show();
                new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                vacateStartTime.setDate(String.format("%d-%s-%s",
                                        year, timeFormat(month + 1),
                                        timeFormat(day)));
                                // System.out.println("ddd");
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });
    }

    /**
     * 审批领导
     */
    private String[] leader_items = new String[]{"冯海锋", "老总", "经理"};

    private void initLeader() {
        initView();
        leader.setTitle("审批领导:");
        leader.setTime("选择审批领导");
        leader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showLeaderChooseDailog();
            }
        });
    }

    protected void showLeaderChooseDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final int leaderP = mPref.getInt("leader", 0);
        builder.setSingleChoiceItems(leader_items, leaderP,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int leaderWhich) {
                        dialog.dismiss();
                        leader.setTime(leader_items[leaderWhich]);
                    }
                });
        builder.show();
    }

    /**
     * 请假类型
     */
    private String[] leave_items = new String[]{"事假", "病假", "婚假"};

    private void initClass() {
        initView();
        vacateType.setTitle("请假类型:");
        vacateType.setTime("选择请假类型");
        vacateType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassChooseDailog();
            }
        });
    }

    /*
     * 弹出类型选项
     */
    protected void showClassChooseDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        int style = mPref.getInt("address_style", 0);// 读取保存的style
        builder.setSingleChoiceItems(leave_items, style,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int classWhich) {
                        dialog.dismiss();// 让dialog消失
                        vacateType.setTime(leave_items[classWhich]);
                    }
                });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        initView();
        TextView tvClass = vacateType.getTvTime();
        TextView tvLeader = leader.getTvTime();
        String textClass = (String) tvClass.getText();
        String textLeader = (String) tvLeader.getText();

        TextView TtvStop = vacateEndTime.getTvTime();
        TextView DtvStop = vacateEndTime.getTvDate();
        String textStopTime = (String) TtvStop.getText();
        String textStopDate = (String) DtvStop.getText();

        TextView TtvStart = vacateStartTime.getTvTime();
        TextView DtvStart = vacateStartTime.getTvDate();
        String textStartTime = (String) TtvStart.getText();
        String textStartDate = (String) DtvStart.getText();

        String startDateTime = textStartDate + " " + textStartTime;
        String stopDateTime = textStopDate + " " + textStopTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
        Date startTime = null;
        Date stopTime = null;
        try {
            startTime = sdf.parse(startDateTime);
            stopTime = sdf.parse(stopDateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (textClass.equals("选择请假类型")) {
            Toast.makeText(getContext(), "请选择请假类型", Toast.LENGTH_LONG).show();
        } else if (textLeader.equals("选择审批领导")) {
            Toast.makeText(getContext(), "请选择审批领导", Toast.LENGTH_LONG).show();
        } else if (textStartTime.equals("选择开始时间")) {
            Toast.makeText(getContext(), "请选择开始时间", Toast.LENGTH_LONG).show();
        } else if (textStopTime.equals("选择结束时间")) {
            Toast.makeText(getContext(), "请选择结束时间", Toast.LENGTH_LONG).show();
        } else if (startTime.getTime() >= stopTime.getTime()) {
            Toast.makeText(getContext(), "结束时间必须大于开始时间", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etContent.getText())) {
            Toast.makeText(getContext(), "请输入请假原因", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_LONG).show();
        }
    }
}
