package com.project.nhom2.booking.Server;

import com.project.nhom2.booking.Bom.RoomBom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/phong.php?=doi&=vip&NgBD=2018-10-18&NgKT=2018-10-19")
    Call<List<RoomBom>> getAnswers(@Query("TenLoaiPhong") String bedType,
                                   @Query("ChatLuong") String roomType,
                                   @Query("NgBD") String checkIn,
                                   @Query("NgKT") String checkOut);

}
