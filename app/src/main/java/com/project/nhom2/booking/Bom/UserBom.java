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

    public UserBom(String a, String b, String c, int d){
        cmnd = a;
        name = b;
        phoneNumber = c;
        userType = d;
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
