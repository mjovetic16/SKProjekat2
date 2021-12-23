package com.example.skpr2.skprojekat2mainservice.repository;

import com.example.skpr2.skprojekat2mainservice.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {
}
