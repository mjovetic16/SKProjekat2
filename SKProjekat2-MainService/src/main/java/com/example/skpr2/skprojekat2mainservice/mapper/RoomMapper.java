package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.dto.RoomDto;
import com.example.skpr2.skprojekat2mainservice.dto.RoomTypeDto;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomMapper() {
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

        return roomType;
    }


}
