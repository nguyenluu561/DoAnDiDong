package com.project.nhom2.booking.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserType {
    private  String IdUserType;
    private  String UserTypeName;
    private  String Coefficient;

}
