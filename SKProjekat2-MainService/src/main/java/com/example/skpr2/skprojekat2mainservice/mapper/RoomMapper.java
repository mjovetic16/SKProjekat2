package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.dto.RoomDto;
import com.example.skpr2.skprojekat2mainservice.dto.RoomTypeDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    private RoomRepository roomRepository;
    private RoomTypeRepository roomTypeRepository;
    private HotelRepository hotelRepository;

    public RoomMapper(RoomTypeRepository roomTypeRepository, RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.hotelRepository = hotelRepository;

    }


    public RoomDto roomToRoomDto(Room room){
        RoomDto roomDto = new RoomDto();

        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType().getId());
        roomDto.setHotel(room.getHotel().getId());
        roomDto.setName(room.getName());

        return roomDto;

    }

    public Room roomDtoToRoom(RoomDto roomDto){
        Room room = new Room();

        room.setId(roomDto.getId());
        room.setName(roomDto.getName());
        room.setHotel(hotelRepository.findById(roomDto.getHotel()).orElseThrow(()->new NotFoundException("Hotel not found")));
        room.setRoomType(roomTypeRepository.findById(roomDto.getRoomType()).orElseThrow(()->new NotFoundException("RoomType not found")));

        return room;
    }

    public RoomTypeDto roomTypeToRoomTypeDto(RoomType roomType){
        RoomTypeDto roomTypeDto = new RoomTypeDto();

        roomTypeDto.setId(roomType.getId());
        roomTypeDto.setName(roomType.getName());
        roomTypeDto.setNumberOfRooms(roomType.getNumberOfRooms());
        roomTypeDto.setPrice(roomType.getPrice());
        roomTypeDto.setHotel(roomType.getHotel().getId());

        return roomTypeDto;
    }

    public RoomType roomTypeDtoToRoomType(RoomTypeDto roomTypeDto){
        RoomType roomType = new RoomType();

        roomType.setId(roomTypeDto.getId());
        roomType.setName(roomTypeDto.getName());
        roomType.setNumberOfRooms(roomTypeDto.getNumberOfRooms());
        roomType.setPrice(roomTypeDto.getPrice());

        System.out.println(roomTypeDto.toString());
        System.out.println(hotelRepository.findById(roomTypeDto.getHotel()));

        roomType.setHotel(hotelRepository.findById(roomTypeDto.getHotel()).orElseThrow(()->new NotFoundException("Hotel not found")));

        return roomType;
    }


}
