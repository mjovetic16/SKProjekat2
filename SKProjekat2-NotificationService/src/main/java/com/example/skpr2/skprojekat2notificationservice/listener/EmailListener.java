package com.example.skpr2.skprojekat2notificationservice.listener;

import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import com.example.skpr2.skprojekat2notificationservice.service.EmailService;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        //MatchesDto matchesDto = messageHelper.getMessage(message, MatchesDto.class);
        //emailService.sendSimpleMessage("mjovetic16@raf.rs", "subject", matchesDto.toString());
    }
}
