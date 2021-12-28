package com.example.skpr2.skprojekat2notificationservice.controller;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }






}

