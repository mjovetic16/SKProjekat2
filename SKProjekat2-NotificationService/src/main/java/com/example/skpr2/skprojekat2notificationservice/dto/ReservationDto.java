package com.example.skpr2.skprojekat2notificationservice.dto;

public class ReservationDto {

    private Long id;

    private Long userID;

    private TerminDto terminDto;

    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public TerminDto getTerminDto() {
        return terminDto;
    }

    public void setTerminDto(TerminDto terminDto) {
        this.terminDto = terminDto;
    }
}
