package com.example.skpr2.skprojekat2mainservice.repository;

import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.domain.Termin;
import com.example.skpr2.skprojekat2mainservice.dto.TerminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {


     Page<Termin> findByCityContainsAndHotelNameContainsAndDayGreaterThanAndDayLessThanAndAccommodationAvailableRoomsGreaterThan(Pageable pageable, String city, String hotel, Date startDate, Date endDate,int zero);

}
