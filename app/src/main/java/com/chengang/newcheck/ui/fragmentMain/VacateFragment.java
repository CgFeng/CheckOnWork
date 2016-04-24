package com.chengang.newcheck.ui.fragmentMain;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.chengang.newcheck.adapter.AttendDetailAdapter;
import com.chengang.newcheck.bean.AttendDetailInfo;
import com.chengang.newcheck.bean.Notification;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.common.STATIC_INFO;
import com.chengang.newcheck.http.AttendDetailHttpHelper;
import com.chengang.newcheck.utils.DateUtil;
import com.chengang.newcheck.widget.AttendPopup;
import com.chengang.newcheck.widget.VacateClickView;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by 陈岗 on 2015/10/22.
 */
public class VacateFragment extends Fragment implements BaseFragment,OnDateSelectedListener, OnMonthChangedListener,Observer {
    private View rootView;
    private MaterialCalendarView materialCalendarView;
    private AttendPopup attendPopup;
    private List<AttendDetailInfo> list = new ArrayList<AttendDetailInfo>();

    private RecyclerView recyclerView;
    private AttendDetailAdapter adapter;
    private LinearLayoutManager manager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.index2, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView();
        return rootView;
    }

    private void initView() {
        materialCalendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        materialCalendarView.setOnDateChangedListener(this);
    }

    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        String thisDate = DateUtil.date2String(getSelectedDatesString(), "yyyy-MM-dd");
        AttendDetailHttpHelper.getAttendDetailById(thisDate, STATIC_INFO.EMPLOYEE_ID,this);
        attendPopup = new AttendPopup(getActivity(),list);
        attendPopup.showPopupWindow();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    @Override
    public void onFragmentActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof List) {
            list.clear();
            list.addAll((Collection<? extends AttendDetailInfo>) data);
        }
    }

    /**
     * 获取某个日期的毫秒数
     */
    private long getSelectedDatesString() {
        CalendarDay date = materialCalendarView.getSelectedDate();

        if (date == null) {
            return 0;
//            return null;
        }
//        return FORMATTER.format(date.getDate());
        return date.getCalendar().getTimeInMillis();
    }
}
