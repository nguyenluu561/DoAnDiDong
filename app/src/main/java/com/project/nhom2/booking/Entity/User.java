package com.project.nhom2.booking.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
@AllArgsConstructor
public class User {
    private String IdCard;
    private String Name;
    private String PhoneNumber;
    private String IdUserType;


}
