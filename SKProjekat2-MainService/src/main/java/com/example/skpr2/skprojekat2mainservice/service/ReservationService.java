package com.example.skpr2.skprojekat2mainservice.service;

import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    Page<ReservationDto> findAll(Pageable pageable);

}
