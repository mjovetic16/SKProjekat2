package com.example.skpr2.skprojekat2mainservice.runner;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private HotelRepository hotelRepository;
    private ReservationRepository reservationRepository;
    private RoomTypeRepository roomTypeRepository;
    private TerminRepository terminRepository;
    private RoomRepository roomRepository;



    public TestDataRunner(HotelRepository hotelRepository, ReservationRepository reservationRepository,
                          RoomTypeRepository roomTypeRepository, TerminRepository terminRepository, RoomRepository roomRepository) {

        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.terminRepository = terminRepository;
        this.roomRepository = roomRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel Beograd");
        hotel1.setDesc("Hotel u Beogradu");
        hotel1.setNumberOfRooms(100);
        hotel1.setId(1L);

        hotelRepository.save(hotel1);

        RoomType roomType = new RoomType();
        roomType.setName("Tip A");
        roomType.setHotel(hotel1);
        roomType.setNumberOfRooms(50);
        roomType.setPrice(100);
        roomType.setId(1L);

        RoomType roomTypeB = new RoomType();
        roomTypeB.setName("Tip B");
        roomTypeB.setHotel(hotel1);
        roomTypeB.setNumberOfRooms(50);
        roomTypeB.setPrice(200);
        roomTypeB.setId(2L);

        List<RoomType> roomTypes = new ArrayList<>();
        roomTypes.add(roomType);
        roomTypes.add(roomTypeB);
        roomTypeRepository.save(roomType);
        roomTypeRepository.save(roomTypeB);



        List<Room> rooms = new ArrayList<>();
        for(int i =1; i<101; i++){

            Room room = new Room();
            room.setId((long) i);
            room.setName("Room "+i);
            if(i<51)
                room.setRoomType(roomType);
            else
                room.setRoomType(roomTypeB);

            room.setHotel(hotel1);

            rooms.add(room);
        }

        roomRepository.saveAll(rooms);

        hotel1.setRooms(rooms);
        hotel1.setRoomTypes(roomTypes);
        hotelRepository.save(hotel1);


    }
}
