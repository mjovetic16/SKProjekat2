package com.example.skpr2.skprojekat2notificationservice.service;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.dto.NotificationTypeDto;
import com.example.skpr2.skprojekat2notificationservice.dto.ReservationUserDto;
import com.example.skpr2.skprojekat2notificationservice.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {


    Page<NotificationTypeDto> findAll(Pageable pageable);

    NotificationTypeDto addType(NotificationTypeDto notificationTypeDto);

    NotificationTypeDto deleteType(NotificationTypeDto notificationTypeDto);

    Notification getRegisterNotification(UserDto userDto);

    Notification getReservationNotification(ReservationUserDto reservationUserDto);

    Notification getCanceledReservationNotification(ReservationUserDto reservationUserDto);

    List<UserDto> getManagers();
}
