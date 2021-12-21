package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ReservationMapper {

    public ReservationMapper() {

    }

    public ReservationDto reservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();

        return reservationDto;
    }

    public Reservation reservationDtoToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();

        return reservation;
    }






}
