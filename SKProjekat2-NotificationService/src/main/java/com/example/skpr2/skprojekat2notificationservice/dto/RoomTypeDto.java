package com.example.skpr2.skprojekat2notificationservice.dto;

public class RoomTypeDto {

    private Long id;

    private String name;

    private int price;

    private int numberOfRooms;

    private Long hotel;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Long getHotel() {
        return hotel;
    }

    public void setHotel(Long hotel) {
        this.hotel = hotel;
    }
}
