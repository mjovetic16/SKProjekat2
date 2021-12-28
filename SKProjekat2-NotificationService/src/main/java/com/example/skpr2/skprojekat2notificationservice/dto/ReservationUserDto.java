package com.example.skpr2.skprojekat2notificationservice.dto;

public class ReservationUserDto {

    private Long id;

    private UserDto userDto;

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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public TerminDto getTerminDto() {
        return terminDto;
    }

    public void setTerminDto(TerminDto terminDto) {
        this.terminDto = terminDto;
    }

    @Override
    public String toString() {
        return "ReservationUserDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", terminDto=" + terminDto +
                ", price=" + price +
                '}';
    }
}
