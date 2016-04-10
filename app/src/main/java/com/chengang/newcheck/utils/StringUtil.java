package com.chengang.newcheck.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by urcha on 2016/3/27.
 */
public class StringUtil {

    /**
     * 判断两个字符串是否一样
     * @param a 字符串a
     * @param b 字符串b
     * @return
     */
    public static boolean isSame(String a,String b){
        if (a.equals(b)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * toast
     * @param context 上下文
     * @param content 内容
     */
    public static void myToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
