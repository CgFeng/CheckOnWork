package com.chengang.newcheck.bean;


import java.io.Serializable;

/**
 * Created by 陈岗 on 2015/10/23.
 * 通知的bean类
 */
public class Notification implements Serializable {
    private String imageNotice;//通知的图片
    private String Head;//通知标题
    private String Subhead;//通知的副标题
    private String AddTime;//通知的添加时间
    private String condition;//通知的等级状态

    public String getImageNotice() {
        return imageNotice;
    }

    public void setImageNotice(String imageNotice) {
        this.imageNotice = imageNotice;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getSubhead() {
        return Subhead;
    }

    public void setSubhead(String subhead) {
        Subhead = subhead;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
