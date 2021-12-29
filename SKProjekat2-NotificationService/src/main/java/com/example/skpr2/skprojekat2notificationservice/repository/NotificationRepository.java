package com.example.skpr2.skprojekat2notificationservice.repository;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findByEmailContainsAndNotificationTypeNameContainsAndDateGreaterThanAndDateLessThan(Pageable pageable, String email, String name, Date startDate, Date endDate);

    Page<Notification> findByEmailContainsAndNotificationTypeNameContainsAndDateGreaterThanAndDateLessThanAndUserIDEquals(Pageable pageable, String email, String name, Date startDate, Date endDate, Long id);


}
