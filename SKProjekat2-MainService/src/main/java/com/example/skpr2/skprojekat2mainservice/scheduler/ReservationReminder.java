package com.example.skpr2.skprojekat2mainservice.scheduler;

import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationReminder {

    private ReservationService reservationService;

    public ReservationReminder(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 100)
    public void remind(){
        System.out.println("Reminder signal sent out");
        reservationService.remindReservation();
    }

}
