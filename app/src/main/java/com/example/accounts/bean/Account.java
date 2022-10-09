package com.example.accounts.bean;

import java.time.LocalDateTime;

/**
 * user: wuzhongxuan
 * date: 2022/10/7
 */
public class Account {
    private Integer _id;

    private String accountname;
    private String username;
    private String password;

    private String createtime;

    public Account(Integer _id, String accountname, String username, String password, String createtime) {
        this._id = _id;
        this.accountname = accountname;
        this.username = username;
        this.password = password;
        this.createtime = createtime;
    }

    public Account(String accountname, String username, String password) {
        this.accountname = accountname;
        this.username = username;
        this.password = password;
    }

    public Account() {
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
