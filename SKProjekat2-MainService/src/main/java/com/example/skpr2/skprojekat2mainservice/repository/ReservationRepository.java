package com.example.skpr2.skprojekat2mainservice.repository;

import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByNotifiedAndTerminDayGreaterThan(boolean notified, Date reservationTime);

}
