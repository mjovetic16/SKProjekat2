package com.example.skpr2.skprojekat2notificationservice.service.impl;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.mapper.NotificationMapper;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationTypeRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.ParameterRepository;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationMapper notificationMapper;
    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;
    private ParameterRepository parameterRepository;

    public NotificationServiceImpl(NotificationMapper notificationMapper, NotificationRepository notificationRepository,
                                   NotificationTypeRepository notificationTypeRepository, ParameterRepository parameterRepository) {

        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.parameterRepository = parameterRepository;
    }




}
