package com.qhshef.counselor;

/**
 * Created by qhshe on 2017-09-02.
 */

public class User {

    String resvDate;
    String userName;
    String userPhone;

    public User(String resvDate, String userName, String userPhone) {
        this.resvDate = resvDate;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public String getResvDate() {
        return resvDate;
    }

    public void setResvDate(String userResvDate) {
        this.resvDate = resvDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
