package com.example.skpr2.skprojekat2notificationservice.dto;

public class RoomDto {

    private Long id;

    private String name;

    private Long hotel;

    private Long roomType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHotel() {
        return hotel;
    }

    public void setHotel(Long hotel) {
        this.hotel = hotel;
    }

    public Long getRoomType() {
        return roomType;
    }

    public void setRoomType(Long roomType) {
        this.roomType = roomType;
    }
}
