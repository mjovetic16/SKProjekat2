package com.example.skpr2.skprojekat2notificationservice.service;

import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.dto.NotificationTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {


    Page<NotificationTypeDto> findAll(Pageable pageable);

    NotificationTypeDto addType(NotificationTypeDto notificationTypeDto);

    NotificationTypeDto deleteType(NotificationTypeDto notificationTypeDto);
}
