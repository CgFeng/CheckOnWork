package com.chengang.newcheck.bean;

import java.util.List;

/**
 * Created by urcha on 2016/4/16.
 */
public class PhotoUploadTest {
    private String userid;
    private String type;
    private List<String> photoList;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
