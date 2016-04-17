package com.chengang.newcheck.bean;

import java.util.ArrayList;

/**
 * 考勤信息
 * Created by urcha on 2016/4/17.
 */
public class AttendInfo {
    private String companyId;
    private String employeeId;
    private String typeId;
    private String latitude;
    private String longitude;
    private String time;//考勤时间,格式如下2015-11-10 15:08:00
    private String distance;
    private String others;
    private ArrayList<String> photoList;

    public AttendInfo(String companyId, String employeeId, String typeId, String latitude, String longitude, String time, String distance, String others, ArrayList<String> photoList) {
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.typeId = typeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.distance = distance;
        this.others = others;
        this.photoList = photoList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public ArrayList<String> getphotoList() {
        return photoList;
    }

    public void setphotoList(ArrayList<String> photoList) {
        this.photoList = photoList;
    }
}
