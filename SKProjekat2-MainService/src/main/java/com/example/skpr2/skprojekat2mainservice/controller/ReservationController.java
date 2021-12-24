package com.example.skpr2.skprojekat2mainservice.controller;


import com.example.skpr2.skprojekat2mainservice.dto.*;
import com.example.skpr2.skprojekat2mainservice.security.CheckSecurity;
import com.example.skpr2.skprojekat2mainservice.service.HotelService;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;
    private HotelService hotelService;

    public ReservationController(ReservationService reservationService, HotelService hotelService) {
        this.reservationService = reservationService;
        this.hotelService = hotelService;
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

        return new ResponseEntity<>(hotelService.findAllHotels(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Update hotel")
    @PostMapping("/hotel/update")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<HotelDto> updateHotel(@RequestHeader("Authorization") String authorization, @RequestBody HotelDto hotelDto) {

        return new ResponseEntity<>(hotelService.updateHotel(hotelDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change room allocation")
    @PostMapping("/hotel/allocation")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<HotelDto> changeAllocation(@RequestHeader("Authorization") String authorization, @RequestBody AllocationDtoRequest allocationDtoRequest) {

        return new ResponseEntity<>(hotelService.changeRoomAllocation(allocationDtoRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all termini")
    @GetMapping("/termin")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<Page<TerminDto>> getAllTermins(@RequestHeader("Authorization") String authorization, Pageable pageable) {

        return new ResponseEntity<>(reservationService.findAllTs(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all filtered")
    @GetMapping("/termin/filter")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<Page<TerminDto>> getAllFiltered(@RequestHeader("Authorization") String authorization, Pageable pageable, FilterDto filterDto) throws ParseException {

        return new ResponseEntity<>(reservationService.findAllFiltered(pageable, filterDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Reserve")
    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> reserve(@RequestHeader("Authorization") String authorization, @RequestBody TerminDto terminDto) {

        return new ResponseEntity<>(reservationService.reserve(authorization,terminDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel reservation")
    @PostMapping("/cancel")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> cancelReservation(@RequestHeader("Authorization") String authorization, @RequestBody ReservationDto reservationDto) {

        return new ResponseEntity<>(reservationService.cancelReservation(authorization,reservationDto), HttpStatus.OK);
    }


}
