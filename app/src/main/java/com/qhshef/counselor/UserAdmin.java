package com.qhshef.counselor;

/**
 * Created by qhshe on 2017-09-11.
 */

public class UserAdmin {

    String resvDate;
    String counName;

    public UserAdmin(String counName, String resvDate) {
        this.counName = counName;
        this.resvDate = resvDate;
    }

    public String getResvDate() {
        return resvDate;
    }

    public void setResvDate(String resvDate) {
        this.resvDate = resvDate;
    }

    public String getCounName() {
        return counName;
    }

    public void setCounName(String counName) {
        this.counName = counName;
    }
}
