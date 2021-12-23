package com.example.skpr2.skprojekat2mainservice.service;

import com.example.skpr2.skprojekat2mainservice.dto.AllocationDtoRequest;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {


    Page<HotelDto> findAllHotels(Pageable pageable);

    HotelDto updateHotel(HotelDto hotelDto);

    HotelDto changeRoomAllocation(AllocationDtoRequest allocationDtoRequest);
}
