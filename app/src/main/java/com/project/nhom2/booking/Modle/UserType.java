package com.project.nhom2.booking.Modle;

public class UserType {
    private  String IdUserType;
    private  String UserTypeName;
    private  String Coefficient;

    public UserType() {
    }

    public UserType(String idUserType, String userTypeName, String coefficient) {
        IdUserType = idUserType;
        UserTypeName = userTypeName;
        Coefficient = coefficient;
    }

    public String getIdUserType() {
        return IdUserType;
    }

    public void setIdUserType(String idUserType) {
        IdUserType = idUserType;
    }

    public String getUserTypeName() {
        return UserTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        UserTypeName = userTypeName;
    }

    public String getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(String coefficient) {
        Coefficient = coefficient;
    }
}
