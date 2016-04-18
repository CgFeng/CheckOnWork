package com.chengang.newcheck.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsInfo implements Parcelable{

    /**
     * ctime : 2015-07-17
     * title : 那个抱走王明涵的，你上微信吗？看完这个你会心软吗?
     * description : 中国传统文化
     * picUrl : http://zxpic.gtimg.com/infonew/0/wechat_pics_-667708.jpg/640
     * url : http://mp.weixin.qq.com/s?__biz=MzA3OTg2NjEwNg==&amp;idx=5&amp;mid=209313388&amp;sn=7e30bd2851d22f69580e202c31fc7ecf
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() { return ctime;}

    public void setCtime(String ctime) { this.ctime = ctime;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;}

    public String getPicUrl() { return picUrl;}

    public void setPicUrl(String picUrl) { this.picUrl = picUrl;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ctime);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.picUrl);
        dest.writeString(this.url);
    }

    public NewsInfo() {}

    protected NewsInfo(Parcel in) {
        this.ctime = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.picUrl = in.readString();
        this.url = in.readString();
    }

    public static final Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel source) {return new NewsInfo(source);}

        @Override
        public NewsInfo[] newArray(int size) {return new NewsInfo[size];}
    };
}
