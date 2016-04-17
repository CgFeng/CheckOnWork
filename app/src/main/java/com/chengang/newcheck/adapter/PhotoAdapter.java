package com.chengang.newcheck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chengang.drawerlayoutdemo.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by urcha on 2015/04/16.
 */
public class PhotoAdapter extends BaseAdapter {
    private  Context context;
    private  List<String> photoList;
    private BitmapUtils bitmapUtils;
    public PhotoAdapter(Context context, List<String> photoList) {
        this.context = context;
        this.photoList = photoList;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public String getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(context, R.layout.view_photo,null);
        }
        ImageView iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
        ImageView iv_m = (ImageView) convertView.findViewById(R.id.iv_m);
        bitmapUtils.display(iv_photo,getItem(position));
        iv_m.setOnClickListener(new DeletePhotoListener(position));
        return convertView;
    }


    class DeletePhotoListener implements View.OnClickListener{
        private int position;
        public DeletePhotoListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            photoList.remove(position);
            PhotoAdapter.this.notifyDataSetChanged();
        }
    }
}