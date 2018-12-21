package com.project.nhom2.booking.Util;

public class StringConfig {

    public static String configString_toNoSign(String text) {
        switch(text) {
            case "Thường": return "thuong";
            case "Đơn": return "don";
            case "Đôi": return "doi";
            default: return "VIP";
        }
    }

    public static String configString_toSign(String text) {
        switch(text) {
            case "thuong": return "Thường";
            case "don": return "Đơn";
            case "doi": return "Đôi";
            default: return "VIP";
        }
    }

    public static String configString_toSign_Report(String text) {
        switch(text) {
            case "Phòng vip": return "&ChatLuong=vip";
            case "Phòng thường": return "&ChatLuong=thuong";
            case "Giường đơn": return "&TenLoaiPhong=don";
            case "Giường đôi": return "&TenLoaiPhong=doi";
            default: return "tong";
        }
    }

    public static String configStringStatus(String text) {
        switch(text) {
            case "da nhan": return "Đang sử dụng";
            default: return "Trống";
        }
    }
}
