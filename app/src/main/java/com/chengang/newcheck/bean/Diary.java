package com.chengang.newcheck.bean;

import java.io.Serializable;

/**
 * Created by Feng Chengang on 2016/4/25.
 */
public class Diary implements Serializable{
    private String createTime;
    private String content;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
