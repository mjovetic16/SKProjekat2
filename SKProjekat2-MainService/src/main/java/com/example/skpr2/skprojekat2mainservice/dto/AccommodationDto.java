package com.example.skpr2.skprojekat2mainservice.dto;

import com.example.skpr2.skprojekat2mainservice.domain.RoomType;

import javax.persistence.ManyToOne;

public class AccommodationDto {

    private Long id;

    private RoomTypeDto roomType;

    private int availableRooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomTypeDto getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeDto roomType) {
        this.roomType = roomType;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }
}
