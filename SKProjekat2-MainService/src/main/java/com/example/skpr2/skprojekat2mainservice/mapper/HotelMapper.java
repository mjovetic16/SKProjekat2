package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HotelMapper {

    private RoomMapper roomMapper;


    public HotelMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public HotelDto hotelToHotelDto(Hotel hotel){
        HotelDto hotelDto = new HotelDto();

        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setDesc(hotel.getDesc());
        hotelDto.setNumberOfRooms(hotel.getNumberOfRooms());
        hotelDto.setRooms(hotel.getRooms().stream().map(roomMapper::roomToRoomDto).collect(Collectors.toList()));
        hotelDto.setRoomTypes(hotel.getRoomTypes().stream().map(roomMapper::roomTypeToRoomTypeDto).collect(Collectors.toList()));

        return hotelDto;

    }

    public Hotel hotelDtoToHotel(HotelDto hotelDto){
        Hotel hotel = new Hotel();

        return hotel;
    }


}
