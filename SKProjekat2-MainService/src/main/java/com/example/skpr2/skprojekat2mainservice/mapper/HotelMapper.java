package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDtoRoomless;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.repository.RoomRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomTypeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    private RoomMapper roomMapper;
    private RoomRepository roomRepository;
    private RoomTypeRepository roomTypeRepository;


    public HotelMapper(RoomMapper roomMapper, RoomRepository roomRepository, RoomTypeRepository roomTypeRepository) {
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
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

    public HotelDtoRoomless hotelToHotelDtoRoomless(Hotel hotel){
        HotelDtoRoomless hotelDto = new HotelDtoRoomless();

        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setDesc(hotel.getDesc());
        hotelDto.setNumberOfRooms(hotel.getNumberOfRooms());
        hotelDto.setRoomTypes(hotel.getRoomTypes().stream().map(roomMapper::roomTypeToRoomTypeDto).collect(Collectors.toList()));

        return hotelDto;

    }

    public Hotel hotelDtoToHotel(HotelDto hotelDto){
        Hotel hotel = new Hotel();

        hotel.setId(hotelDto.getId());
        hotel.setName(hotelDto.getName());
        hotel.setDesc(hotelDto.getDesc());
        hotel.setNumberOfRooms(hotelDto.getNumberOfRooms());

        ArrayList<RoomType> roomTypes = new ArrayList<>();
        hotelDto.getRoomTypes().forEach((roomTypeDto -> {roomTypes.add(roomTypeRepository.findById(roomTypeDto.getId()).orElseThrow(()->new NotFoundException("Nije pronadjen tip sobe")));}));
        hotel.setRoomTypes(roomTypes);

        ArrayList<Room> rooms = new ArrayList<>();
        hotelDto.getRooms().forEach(room->{rooms.add(roomRepository.findById(room.getId()).orElseThrow(()->new NotFoundException("Nije pronadjena soba")));});
        hotel.setRooms(rooms);

        return hotel;
    }


}
