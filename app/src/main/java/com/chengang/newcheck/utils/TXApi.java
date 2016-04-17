package com.chengang.newcheck.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by urcha on 2016/4/16.
 */
public class TXApi {

    public static final int SOCIAL = 0x10;
    public static final int CHN = 0x11;
    public static final int NATIONAL = 0x12;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ SOCIAL, CHN, NATIONAL })
    public @interface NewsType {}

    private TXApi() throws InstantiationException {
        throw new InstantiationException("工具类，不需要new哦");
    }

    public static String getDevKey() {
        return "a03112858c73f5c7078d4039850dbf4a";
    }

    /**
     * 微信热门精选数据
     * http://api.huceo.com/wxnew/?key=APIKEY&num=10
     */
    public static String getWXNew() {
        return "http://api.huceo.com/wxnew/";
    }

    /**
     * 新闻数据
     */
    public static String getNews(@NewsType int type) {
        switch (type) {
            case SOCIAL:
                return "http://api.huceo.com/social/";
            case CHN:
                return "http://api.huceo.com/guonei/";
            case NATIONAL:
                return "http://api.huceo.com/world/";
        }
        return "";
    }

    /**
     * 体育新闻数据
     */
    public static String getSportNews() {
        return "http://api.huceo.com/tiyu/";
    }

    /**
     * 娱乐花边数据
     */
    public static String getEntertainment() {
        return "http://api.huceo.com/huabian/";
    }

    /**
     * 美女图片数据
     */
    public static String getSexyPic() {
        return "http://api.huceo.com/meinv/";
    }

    /**
     * 科技新闻数据
     */
    public static String getTechNews() {
        return "http://api.huceo.com/keji/";
    }

    /**
     * 另类←_←
     */
    public static String getStrangeNews() {
        return "http://api.huceo.com/qiwen/";
    }

    /**
     * 健康资讯数据
     */
    public static String getHealthNews() {
        return "http://api.huceo.com/health/";
    }

    /**
     * 旅游热点数据
     */
    public static String getTravelNews() {
        return "http://api.huceo.com/travel/";
    }
}
