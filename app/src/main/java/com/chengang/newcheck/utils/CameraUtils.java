package com.chengang.newcheck.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chengang.drawerlayoutdemo.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chengang on 2015/10/21.
 */
public class CameraUtils {
    public static final int CHOOSE_FROM_CAMERA = 1;
    public static final int CHOOSE_FROM_ALBUM = 2;
    public static final int PHOTO_CROP = 3;
    public static Uri imgUri;
    public static File img;
    public static File sdRoot;
    public static File mPhotos;

    /**
     * 调用摄像头
     */
    public static void takePhotos(Activity activity) {
        //判断外置存储的状态
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            sdRoot = Environment.getExternalStorageDirectory();
            mPhotos = new File(sdRoot, "Photos");
            if (!mPhotos.exists()) {
                mPhotos.mkdirs();
            }

            //为每张图片起一个唯一的名字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String photoName = sdf.format(new Date()) + ".jpg";

            //调用摄像头
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //创建保存图片的对象
            img = new File(mPhotos, photoName);
            //把图片保存为uri格式
            imgUri = Uri.fromFile(img);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            activity.startActivityForResult(i, CHOOSE_FROM_CAMERA);
        }
    }

    /**
     * 从图库取出照片
     */
    public static void takePhotoFromAlbum(Activity activity) {
        //判断外置存储的状态
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            sdRoot = Environment.getExternalStorageDirectory();
            mPhotos = new File(sdRoot, "Photos");
            if (!mPhotos.exists()) {
                mPhotos.mkdirs();
            }

            //为每张图片起一个唯一的名字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String photoName = sdf.format(new Date()) + ".jpg";

            //调用摄像头
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //创建保存图片的对象
            img = new File(mPhotos, photoName);
            //把图片保存为uri格式
            imgUri = Uri.fromFile(img);

            Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            albumIntent.setType("image/*");
            albumIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            activity.startActivityForResult(albumIntent, CHOOSE_FROM_ALBUM);
        }
    }

    /**
     * 图片裁剪
     */
    public static void cropPhotos(Activity activity, Uri imgUri) {

        View view = LayoutInflater.from(activity).inflate(R.layout.activity_me, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_me);
        //呼叫裁剪应用程序
        Intent i = new Intent("com.android.camera.action.CROP");
        final float scale = activity.getResources().getDisplayMetrics().density;
        i.setDataAndType(imgUri, "image/*");
        i.putExtra("scale", true);
        i.putExtra("aspectX", 4);
        i.putExtra("aspectY", 3);
        i.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.imgUri);//裁剪后重新保存
        activity.startActivityForResult(i, PHOTO_CROP);
    }

    /**
     * 存储图片路径
     */
    public static void saveMyPhoto(Context context, String imgFile) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences("imageUri", Context.MODE_PRIVATE);
        //存入数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageUri", imgFile);
        editor.commit();
    }

    /**
     * 取得图片路径
     */
    public static String getMyPhoto(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("imageUri", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.getString("imageUri", "");
    }

    /**
     * 设置图片显示的一些设置
     */
    public static Bitmap getBitmap(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;//十分之一压缩
        Bitmap bitmap = BitmapFactory.decodeFile(getMyPhoto(context), options);
        return bitmap;
    }

    /**
     * Uri获取真实路径
     */
    public static String getImagePath(Context context, Intent data) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(data.getData(), proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
