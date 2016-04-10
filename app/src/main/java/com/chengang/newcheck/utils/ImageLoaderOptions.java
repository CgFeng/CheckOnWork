package com.chengang.newcheck.utils;

import com.chengang.drawerlayoutdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Administrator on 2015/10/21.
 */
public class ImageLoaderOptions {
    private static DisplayImageOptions options;
    public static void initURLOptions() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .showImageForEmptyUri(R.drawable.default_pic)
                .showImageOnFail(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }
}
