package com.chengang.newcheck.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.utils.BasePopup;
import com.chengang.newcheck.utils.CameraUtils;
import com.chengang.newcheck.utils.ImageLoaderOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MeActivity extends AppCompatActivity implements BasePopup.OnDismissListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    CoordinatorLayout rootLayout;
    TabLayout tabLayout;
    FloatingActionButton fabBtn;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NavigationView navigationView;
    ImageView imageView;
    private MyPopuWindow myPopuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ImageLoaderOptions.initURLOptions();
        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPopuWindow = new MyPopuWindow(this);
        myPopuWindow.setOnDismissListener(this);
        myPopuWindow.setFocusable(true);
        myPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        myPopuWindow.setOutsideTouchable(true);
        myPopuWindow.update();
        imageView= (ImageView) findViewById(R.id.iv_me);
        ImageLoader.getInstance().displayImage("file://"+CameraUtils.getMyPhoto(this),imageView);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("我的");



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = (CoordinatorLayout ) findViewById(R.id.rootLayout);
        fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }
        if(id==R.id.action_choosephoto){

            myPopuWindow.showPopupWindow();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== CameraUtils.CHOOSE_FROM_CAMERA){
            if(resultCode ==RESULT_OK){
                //呼叫裁剪应用程序
                CameraUtils.cropPhotos(this,CameraUtils.imgUri);
            }
        }
        if(requestCode==CameraUtils.PHOTO_CROP){
            if(resultCode==RESULT_OK){
                    CameraUtils.saveMyPhoto(this, CameraUtils.img.getAbsolutePath());
//                imageView.setImageBitmap(CameraUtils.getBitmap(this));
                ImageLoader.getInstance().displayImage("file://" + CameraUtils.getMyPhoto(this), imageView);

            }
        }
        if(requestCode==CameraUtils.CHOOSE_FROM_ALBUM){
            if(resultCode == RESULT_OK && data != null){

                    CameraUtils.cropPhotos(this, data.getData());
//                imageView.setImageBitmap(CameraUtils.getBitmap(this));
//                ImageLoader.getInstance().displayImage("file://"+CameraUtils.getMyPhoto(this),imageView);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDismiss() {
        myPopuWindow.dismiss();
    }
}