package com.project.nhom2.booking.Util;

import com.project.nhom2.booking.Server.RetrofitClient;
import com.project.nhom2.booking.Server.SOService;

public class ApiUtils {
    public static final String BASE_URL = "https://datphong12.000webhostapp.com";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
