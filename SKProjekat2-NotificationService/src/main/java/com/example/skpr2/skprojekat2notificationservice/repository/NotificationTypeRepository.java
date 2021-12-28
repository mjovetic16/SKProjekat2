package com.example.skpr2.skprojekat2notificationservice.repository;

import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {
    Optional<NotificationType> findByName(String name);
}
