package com.example.skpr2.skprojekat2mainservice.dto;

import java.util.List;

public class AllocationDto {

    private RoomTypeDto roomTypeDto;
    private int startIndex;
    private int endIndex;
    private List<Integer> indexList;
    private boolean isRanged;
    private HotelDto hotelDto;

    public HotelDto getHotelDto() {
        return hotelDto;
    }

    public void setHotelDto(HotelDto hotelDto) {
        this.hotelDto = hotelDto;
    }

    public RoomTypeDto getRoomTypeDto() {
        return roomTypeDto;
    }

    public void setRoomTypeDto(RoomTypeDto roomTypeDto) {
        this.roomTypeDto = roomTypeDto;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Integer> indexList) {
        this.indexList = indexList;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public void setRanged(boolean ranged) {
        isRanged = ranged;
    }
}
