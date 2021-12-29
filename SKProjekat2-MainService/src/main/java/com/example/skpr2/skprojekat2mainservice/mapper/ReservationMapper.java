package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Accommodation;
import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.domain.Termin;
import com.example.skpr2.skprojekat2mainservice.dto.AccommodationDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationUserDto;
import com.example.skpr2.skprojekat2mainservice.dto.TerminDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.repository.AccommodationRepository;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomTypeRepository;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import com.example.skpr2.skprojekat2mainservice.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    private HotelRepository hotelRepository;
    private UserService userService;
    private AccommodationRepository accommodationRepository;
    private RoomTypeRepository roomTypeRepository;
    private RoomMapper roomMapper;
    private HotelMapper hotelMapper;

    public ReservationMapper(HotelRepository hotelRepository, AccommodationRepository accommodationRepository, RoomMapper roomMapper,
                             RoomTypeRepository roomTypeRepository, HotelMapper hotelMapper, UserService userService) {

        this.hotelRepository = hotelRepository;
        this.accommodationRepository = accommodationRepository;
        this.roomMapper = roomMapper;
        this.hotelMapper = hotelMapper;
        this.roomTypeRepository = roomTypeRepository;
        this.userService = userService;


    }

    public ReservationDto reservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setUserID(reservation.getUserID());
        reservationDto.setId(reservation.getId());
        reservationDto.setTerminDto(terminToTerminDto(reservation.getTermin()));
        reservationDto.setPrice(reservation.getPrice());
        reservationDto.setNotified(reservation.isNotified());

        return reservationDto;
    }

    public Reservation reservationDtoToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();

        reservation.setId(reservationDto.getId());
        reservation.setUserID(reservationDto.getUserID());
        reservation.setTermin(terminDtoToTermin(reservationDto.getTerminDto()));
        reservation.setPrice(reservationDto.getPrice());
        reservation.setNotified(reservationDto.isNotified());

        return reservation;
    }

    public TerminDto terminToTerminDto(Termin termin){
        TerminDto terminDto = new TerminDto();

        terminDto.setId(termin.getId());
        terminDto.setCity(termin.getCity());
        terminDto.setHotel(hotelMapper.hotelToHotelDtoRoomless(termin.getHotel()));
        terminDto.setDay(termin.getDay());

        terminDto.setAccommodationDto(accommodationToAccommodationDto(termin.getAccommodation()));

        return terminDto;
    }

    public Termin terminDtoToTermin(TerminDto terminDto){
        Termin termin = new Termin();

        termin.setId(terminDto.getId());
        termin.setCity(terminDto.getCity());
        termin.setDay(terminDto.getDay());


        termin.setAccommodation(accommodationRepository.findById(terminDto.getAccommodationDto().getId()).orElseThrow(()->new NotFoundException("Nije pronadjen smestaj")));
        termin.setHotel(hotelRepository.findById(terminDto.getHotel().getId()).orElseThrow(()->new NotFoundException("Nije pronadjen hotel")));


        return termin;
    }

    public AccommodationDto accommodationToAccommodationDto(Accommodation accommodation){
        AccommodationDto accommodationDto = new AccommodationDto();

        accommodationDto.setId(accommodation.getId());
        accommodationDto.setAvailableRooms(accommodation.getAvailableRooms());
        accommodationDto.setRoomType(roomMapper.roomTypeToRoomTypeDto(accommodation.getRoomType()));

        return accommodationDto;

    }

    public Accommodation accommodationDtoToAccommodation(AccommodationDto accommodationDto){

        Accommodation accommodation = new Accommodation();

        accommodation.setId(accommodationDto.getId());
        accommodation.setAvailableRooms(accommodationDto.getAvailableRooms());
        accommodation.setRoomType(roomTypeRepository.findById(accommodationDto.getRoomType().getId()).orElseThrow(()->new NotFoundException("RoomType not found")));

        return accommodation;

    }

    public ReservationUserDto reservationUserDtoFromReservationDto(ReservationDto reservationDto){
        ReservationUserDto reservationUserDto = new ReservationUserDto();

        reservationUserDto.setId(reservationDto.getId());
        reservationUserDto.setTerminDto(reservationDto.getTerminDto());
        reservationUserDto.setPrice(reservationDto.getPrice());
        reservationUserDto.setUserDto(userService.getUser(reservationDto.getUserID()));

        return reservationUserDto;

    }




}
