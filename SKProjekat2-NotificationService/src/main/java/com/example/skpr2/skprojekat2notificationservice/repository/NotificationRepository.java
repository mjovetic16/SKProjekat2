package com.example.skpr2.skprojekat2notificationservice.repository;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
