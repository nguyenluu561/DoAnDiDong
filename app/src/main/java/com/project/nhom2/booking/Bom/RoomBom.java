package com.project.nhom2.booking.Bom;

import android.support.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RoomBom {
    private String id;
    private String bedtype;
    private String roomtype;
    private int price;
    @Nullable
    private String status;
}
