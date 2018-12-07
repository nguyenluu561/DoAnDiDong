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
}
