package com.example.skpr2.skprojekat2mainservice.controller;


import com.example.skpr2.skprojekat2mainservice.dto.AllocationDto;
import com.example.skpr2.skprojekat2mainservice.dto.AllocationDtoRequest;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.security.CheckSecurity;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation(value = "Get all reservations")
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<Page<ReservationDto>> getAllReservations(@RequestHeader("Authorization") String authorization,
                                                                   Pageable pageable) {

        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all hotels")
    @GetMapping("/hotel")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<Page<HotelDto>> getAllHotels(@RequestHeader("Authorization") String authorization, Pageable pageable) {

        return new ResponseEntity<>(reservationService.findAllHotels(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Update hotel")
    @PostMapping("/hotel/update")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<HotelDto> updateHotel(@RequestHeader("Authorization") String authorization, @RequestBody HotelDto hotelDto) {

        return new ResponseEntity<>(reservationService.updateHotel(hotelDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change room allocation")
    @PostMapping("/hotel/allocation")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<HotelDto> changeAllocation(@RequestHeader("Authorization") String authorization, @RequestBody AllocationDtoRequest allocationDtoRequest) {

        return new ResponseEntity<>(reservationService.changeRoomAllocation(allocationDtoRequest), HttpStatus.OK);
    }



}
