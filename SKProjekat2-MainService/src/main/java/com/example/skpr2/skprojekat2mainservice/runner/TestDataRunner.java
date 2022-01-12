package com.example.skpr2.skprojekat2mainservice.runner;

import com.example.skpr2.skprojekat2mainservice.domain.*;
import com.example.skpr2.skprojekat2mainservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private HotelRepository hotelRepository;
    private ReservationRepository reservationRepository;
    private RoomTypeRepository roomTypeRepository;
    private TerminRepository terminRepository;
    private ReviewRepository reviewRepository;
    private RoomRepository roomRepository;
    private AccommodationRepository accommodationRepository;



    public TestDataRunner(HotelRepository hotelRepository, ReservationRepository reservationRepository,
                          RoomTypeRepository roomTypeRepository, TerminRepository terminRepository, RoomRepository roomRepository,
                          AccommodationRepository accommodationRepository, ReviewRepository reviewRepository) {

        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.terminRepository = terminRepository;
        this.roomRepository = roomRepository;
        this.accommodationRepository = accommodationRepository;
        this.reviewRepository = reviewRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        //Dodavanje hotela
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel Beograd");
        hotel1.setDesc("Hotel u Beogradu");
        hotel1.setNumberOfRooms(100);
        hotel1.setId(1L);

        hotelRepository.save(hotel1);

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel Novi Sad");
        hotel2.setDesc("Hotel u Novom Sadu");
        hotel2.setNumberOfRooms(80);
        hotel2.setId(2L);

        hotelRepository.save(hotel2);

        //Dodavanje roomtype-a
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


        //Dodavanje room-a
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

        roomTypes.remove(roomType);
        hotel2.setRooms(rooms);
        hotel2.setRoomTypes(roomTypes);
        hotelRepository.save(hotel2);

        //Dodavanje smestaja
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setId(1L);
        accommodation1.setRoomType(roomType);
        accommodation1.setAvailableRooms(50);

        Accommodation accommodation2 = new Accommodation();
        accommodation2.setId(2L);
        accommodation2.setRoomType(roomTypeB);
        accommodation2.setAvailableRooms(50);

        Accommodation accommodation3 = new Accommodation();
        accommodation3.setId(3L);
        accommodation3.setRoomType(roomTypeB);
        accommodation3.setAvailableRooms(1);

        Accommodation accommodation4 = new Accommodation();
        accommodation4.setId(4L);
        accommodation4.setRoomType(roomType);
        accommodation4.setAvailableRooms(50);

        Accommodation accommodation5 = new Accommodation();
        accommodation5.setId(5L);
        accommodation5.setRoomType(roomTypeB);
        accommodation5.setAvailableRooms(50);

        Accommodation accommodation6 = new Accommodation();
        accommodation6.setId(6L);
        accommodation6.setRoomType(roomType);
        accommodation6.setAvailableRooms(0);

        Accommodation accommodation7 = new Accommodation();
        accommodation7.setId(7L);
        accommodation7.setRoomType(roomTypeB);
        accommodation7.setAvailableRooms(0);

        accommodationRepository.save(accommodation1);
        accommodationRepository.save(accommodation2);
        accommodationRepository.save(accommodation3);
        accommodationRepository.save(accommodation4);
        accommodationRepository.save(accommodation5);
        accommodationRepository.save(accommodation6);
        accommodationRepository.save(accommodation7);

        //Dodavanje termina
        Termin termin1 = new Termin();
        termin1.setId(1L);
        termin1.setHotel(hotel1);
        termin1.setDay(new Date());
        termin1.setCity("Beograd");


        termin1.setAccommodation(accommodation1);


        Termin termin2 = new Termin();
        termin2.setId(2L);
        termin2.setHotel(hotel2);
        termin2.setDay(new Date());
        termin2.setCity("Novi Sad");


        termin2.setAccommodation(accommodation2);


        Termin termin3 = new Termin();
        termin3.setId(3L);
        termin3.setHotel(hotel1);
        termin3.setDay(new Date());
        termin3.setCity("Beograd");


        termin3.setAccommodation(accommodation3);


        terminRepository.save(termin1);
        terminRepository.save(termin2);
        terminRepository.save(termin3);

        //Dodavanje review-a
        Review review = new Review();
        review.setId(1L);
        review.setGrade(3);
        review.setCity("Beograd");
        review.setComment("Odlican hotel, puna preporuka.");
        review.setHotel(hotel1);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setGrade(4);
        review2.setCity("Novi Sad");
        review2.setComment("Nije lose");
        review2.setHotel(hotel2);

        Review review3 = new Review();
        review3.setId(3L);
        review3.setGrade(1);
        review3.setCity("Novi Sad");
        review3.setComment("Solidno lose");
        review3.setHotel(hotel2);

        reviewRepository.save(review);
        reviewRepository.save(review2);
        reviewRepository.save(review3);




    }
}
