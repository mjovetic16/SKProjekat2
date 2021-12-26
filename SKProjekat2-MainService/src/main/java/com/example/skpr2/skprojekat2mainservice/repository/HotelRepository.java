package com.example.skpr2.skprojekat2mainservice.repository;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
