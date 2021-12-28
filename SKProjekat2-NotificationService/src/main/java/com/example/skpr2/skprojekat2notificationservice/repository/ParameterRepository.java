package com.example.skpr2.skprojekat2notificationservice.repository;

import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter,Long> {

    Optional<Parameter> findByName(String name);
}
