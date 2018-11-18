package com.project.nhom2.booking.Entity;

public class User {
    private String IdCard;
    private String Name;
    private String PhoneNumber;
    private String IdUserType;

    public User() {
    }

    public User(String idCard, String name, String phoneNumber, String idUserType) {
        IdCard = idCard;
        Name = name;
        PhoneNumber = phoneNumber;
        IdUserType = idUserType;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getIdUserType() {
        return IdUserType;
    }

    public void setIdUserType(String idUserType) {
        IdUserType = idUserType;
    }
}
