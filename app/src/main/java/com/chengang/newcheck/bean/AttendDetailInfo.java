package com.chengang.newcheck.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by urcha on 2016/4/24.
 */
public class AttendDetailInfo {
    private String time;//考勤时间,格式如下2015-11-10 15:08:00
    private String attendType;
    private String distance;
    private String others;
    private String attendTag;//考勤标记
    private List<String> photoList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAttendType() {
        return attendType;
    }

    public void setAttendType(String attendType) {
        this.attendType = attendType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getAttendTag() {
        return attendTag;
    }

    public void setAttendTag(String attendTag) {
        this.attendTag = attendTag;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
