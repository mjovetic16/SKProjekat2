package com.example.skpr2.skprojekat2notificationservice.controller;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.dto.NotificationTypeDto;
import com.example.skpr2.skprojekat2notificationservice.security.CheckSecurity;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @ApiOperation(value = "Get all notification types")
    @GetMapping("/types")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<Page<NotificationTypeDto>> getAllNotifications(@RequestHeader("Authorization") String authorization,
                                                                        Pageable pageable) {

       return new ResponseEntity<>(notificationService.findAll(pageable), HttpStatus.OK);


    }





}

