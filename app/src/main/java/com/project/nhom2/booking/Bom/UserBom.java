package com.project.nhom2.booking.Bom;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserBom {

    private String cmnd;
    private String name;
    private String phoneNumber;
    private int userType;
    private int history;


    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }


    public UserBom(String a, String b, String c, int d,int e){
        cmnd = a;
        name = b;
        phoneNumber = c;
        userType = d;
        history = e;
    };

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
