package com.example.skpr2.skprojekat2notificationservice.listener;

import com.example.skpr2.skprojekat2notificationservice.dto.UserDto;
import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import com.example.skpr2.skprojekat2notificationservice.service.impl.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class RegisterListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public RegisterListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.registerNotify}", concurrency = "5-10")
    public void registerNotify(Message message) throws JMSException {

        UserDto userDto = messageHelper.getMessage(message, UserDto.class);

        String recepient = "mjovetic16@raf.rs";
        String subject = "Confirm registration";
        String recepientOg = userDto.getEmail();

        emailService.sendSimpleMessage(recepient, subject, userDto.toString()+"\n Recepient:"+recepientOg);
    }
}