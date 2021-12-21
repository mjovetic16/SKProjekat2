package com.example.skpr2.skprojekat2mainservice.controller;


import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.security.CheckSecurity;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
