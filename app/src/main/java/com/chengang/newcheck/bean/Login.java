package com.chengang.newcheck.bean;


import java.io.Serializable;

/**
 * Created by 陈岗 on 2015/10/23.
 * 通知的bean类
 */
public class Login implements Serializable {
    private String account;//账号
    private String password;//密码

    public Login(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
