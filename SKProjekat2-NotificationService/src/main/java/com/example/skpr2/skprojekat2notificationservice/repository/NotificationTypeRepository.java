package com.example.skpr2.skprojekat2notificationservice.repository;

import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {
}
