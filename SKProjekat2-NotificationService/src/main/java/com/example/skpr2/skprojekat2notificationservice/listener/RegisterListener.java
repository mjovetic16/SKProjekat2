package com.example.skpr2.skprojekat2notificationservice.listener;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.dto.UserDto;
import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import com.example.skpr2.skprojekat2notificationservice.service.impl.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class RegisterListener {

    private MessageHelper messageHelper;
    private EmailService emailService;
    private NotificationService notificationService;

    public RegisterListener(MessageHelper messageHelper, EmailService emailService, NotificationService notificationService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "${destination.registerNotify}", concurrency = "5-10")
    public void registerNotify(Message message) throws JMSException {

        UserDto userDto = messageHelper.getMessage(message, UserDto.class);

        Notification notification = notificationService.getRegisterNotification(userDto);

        //TODO notify manager

        emailService.sendSimpleMessage("mjovetic16@raf.rs", "Confirm Registration", notification.getText());
    }
}