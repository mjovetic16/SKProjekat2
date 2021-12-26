package com.example.skpr2.skprojekat2mainservice.dto;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;

import javax.persistence.ManyToOne;

public class ReviewDto {

    private Long id;


    private HotelDtoRoomless hotel;

    private String city;

    private int grade;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelDtoRoomless getHotel() {
        return hotel;
    }

    public void setHotel(HotelDtoRoomless hotel) {
        this.hotel = hotel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
