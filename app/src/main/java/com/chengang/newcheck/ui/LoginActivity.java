package com.chengang.newcheck.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.chengang.drawerlayoutdemo.R;
import com.chengang.newcheck.bean.Login;
import com.chengang.newcheck.common.DICT;
import com.chengang.newcheck.http.LoginHttpHelper;
import com.chengang.newcheck.ui.fragmentMain.IndexFragment;
import com.chengang.newcheck.ui.index.AttendFragment;
import com.chengang.newcheck.utils.StringUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener,Observer {
    private EditText etAccount;
    private EditText etPassword;
    private String account;
    private String password;
    private Button btn_login;
    private Toolbar toolbar;

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();

        //返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etAccount = (EditText) findViewById(R.id.et_login_phone);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }


    /**
     * 判断是否可以登录
     */
    private void attemptLogin() {

        etAccount.setError(null);
        etPassword.setError(null);

        account = etAccount.getText().toString();
        password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("密码不能为空");
            focusView = etPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(account)) {
            etAccount.setError("账号不能为空");
            focusView = etAccount;
            cancel = true;
        }

        if (cancel) {
            //焦点回到最初的view.
            focusView.requestFocus();
        } else {
            //请求服务器的代买写在这里
            asyncLogin(new Login(account,password));
        }
    }

    /**
     * 登陆
     */
    private void asyncLogin(Login login) {
        LoginHttpHelper.submitLogin(login, this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.btn_login:
                attemptLogin();
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if((StringUtil.isSame((String) data, DICT.LOGIN_SUCCESS))){
            //登录成功
            StringUtil.myToast(this,DICT.LOGIN_SUCCESS_INFO);
            //跳转到首页
            Intent toIndex = new Intent(this, MainActivity.class);
            startActivity(toIndex);
        }else{
            //登录失败
            StringUtil.myToast(this,DICT.LOGIN_FAILURE_INFO);
        }
    }
}

