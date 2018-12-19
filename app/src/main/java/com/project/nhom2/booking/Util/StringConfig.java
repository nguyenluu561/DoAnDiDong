package com.project.nhom2.booking.Util;

public class StringConfig {

    public static String configString_toNoSign(String text) {
        switch(text) {
            case "Thường": return "thuong";
            case "Đơn": return "don";
            default: return "doi";
        }
    }

    public static String configString_toSign(String text) {
        switch(text) {
            case "thuong": return "Thường";
            case "don": return "Đơn";
            default: return "Đôi";
        }
    }

    public static String configStringStatus(String text) {
        switch(text) {
            case "chua nhan": return "Chưa nhận";
            default: return "Đang sử dụng";
        }
    }
}
