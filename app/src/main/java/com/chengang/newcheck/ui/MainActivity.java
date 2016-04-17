package com.chengang.newcheck.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.ui.fragmentMain.BaseFragment;
import com.chengang.newcheck.ui.fragmentMain.FourthFragment;
import com.chengang.newcheck.ui.fragmentMain.IndexFragment;
import com.chengang.newcheck.ui.fragmentMain.NoticeFragment;
import com.chengang.newcheck.ui.fragmentMain.VacateFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private LinkedList<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        fragmentList=new LinkedList<>();

        initView();
        switchToIndex();
    }

    private void initView() {
        //设置Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //设置DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.hello_world, R.string.hello_world);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        //设置NavigationView点击事件
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        addFragment2List();

    }

    private void addFragment2List() {
        //顺序
        fragmentList.add(new IndexFragment());
        fragmentList.add(new VacateFragment());
        fragmentList.add(new NoticeFragment());
        fragmentList.add(new FourthFragment());


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.navItem1:
                                switchToIndex();
                                break;
                            case R.id.navItem2:
                                switchToSecond();
                                break;
                            case R.id.navItem3:
                                switchToThird();
                                break;
                            case R.id.navItem4:
                                switchToSFourth();
                                break;
                        }
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    /**
     * 第一个fragment页面
     */
    private void switchToIndex() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, (Fragment) fragmentList.get(0)).commit();
        toolbar.setTitle("首页");
    }

    /**
     * 第二个fragment页面
     */
    private void switchToSecond() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, (Fragment) fragmentList.get(1)).commit();
        toolbar.setTitle("请假");
    }

    /**
     * 第三个fragment页面
     */
    private void switchToThird() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, (Fragment) fragmentList.get(2)).commit();
        toolbar.setTitle("公告");
    }

    /**
     * 第四个fragment页面
     */
    private void switchToSFourth() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, (Fragment) fragmentList.get(3)).commit();
        toolbar.setTitle("设置");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (BaseFragment baseFragment : fragmentList) {
            baseFragment.onFragmentActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_me:
                startActivity(new Intent(this, MeActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
