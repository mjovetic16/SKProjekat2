package com.example.skpr2.skprojekat2mainservice.service;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {

    Page<ReservationDto> findAll(Pageable pageable);

    Page<TerminDto> findAllTs(Pageable pageable);

    Page<TerminDto> findAllFiltered(Pageable pageable, FilterDto filterDto) throws ParseException;

    ReservationDto reserve(String authorization, TerminDto terminDto);

    ReservationDto cancelReservation(String authorization, ReservationDto reservationDto);
}
