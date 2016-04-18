package com.chengang.newcheck.bean;


import java.io.Serializable;

/**
 * Created by 陈岗 on 2015/10/23.
 * 通知的bean类
 */
public class Notification implements Serializable {
    private String title;
    private String content;
    private String publishTime;
    private String noticImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getNoticImage() {
        return noticImage;
    }

    public void setNoticImage(String noticImage) {
        this.noticImage = noticImage;
    }
}
