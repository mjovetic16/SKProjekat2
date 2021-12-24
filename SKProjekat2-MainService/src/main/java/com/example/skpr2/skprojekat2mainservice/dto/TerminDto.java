package com.example.skpr2.skprojekat2mainservice.dto;

import com.example.skpr2.skprojekat2mainservice.domain.Accommodation;
import com.example.skpr2.skprojekat2mainservice.domain.Hotel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class TerminDto {

    private Long id;

    private String city;

    private HotelDtoRoomless hotel;

    private Date day;

    private AccommodationDto accommodationDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HotelDtoRoomless getHotel() {
        return hotel;
    }

    public void setHotel(HotelDtoRoomless hotel) {
        this.hotel = hotel;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public AccommodationDto getAccommodationDto() {
        return accommodationDto;
    }

    public void setAccommodationDto(AccommodationDto accommodationDto) {
        this.accommodationDto = accommodationDto;
    }
}
