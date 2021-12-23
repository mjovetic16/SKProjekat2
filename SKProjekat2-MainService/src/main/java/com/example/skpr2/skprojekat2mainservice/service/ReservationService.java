package com.example.skpr2.skprojekat2mainservice.service;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    Page<ReservationDto> findAll(Pageable pageable);

    Page<HotelDto> findAllHotels(Pageable pageable);

    HotelDto updateHotel(HotelDto hotelDto);
}
