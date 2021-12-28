package com.example.skpr2.skprojekat2notificationservice.listener;

import com.example.skpr2.skprojekat2notificationservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2notificationservice.dto.ReservationUserDto;
import com.example.skpr2.skprojekat2notificationservice.dto.UserDto;
import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import com.example.skpr2.skprojekat2notificationservice.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class ReservationListener {
    private MessageHelper messageHelper;
    private EmailService emailService;

    public ReservationListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.reservationNotify}", concurrency = "5-10")
    public void reservationNotify(Message message) throws JMSException {

        ReservationUserDto reservationUserDto = messageHelper.getMessage(message, ReservationUserDto.class);

        UserDto userDto = reservationUserDto.getUserDto();

        String recepient = "mjovetic16@raf.rs";
        String subject = "Confirm reservation";
        String recepientOg = userDto.getEmail();

        emailService.sendSimpleMessage(recepient, subject, reservationUserDto.toString()+"\n Recepient:"+recepientOg);
    }

    @JmsListener(destination = "${destination.cancelReservationNotify}", concurrency = "5-10")
    public void cancelReservationNotify(Message message) throws JMSException {

        ReservationUserDto reservationUserDto = messageHelper.getMessage(message, ReservationUserDto.class);

        UserDto userDto = reservationUserDto.getUserDto();

        String recepient = "mjovetic16@raf.rs";
        String subject = "Canceled reservation";
        String recepientOg = userDto.getEmail();

        emailService.sendSimpleMessage(recepient, subject, reservationUserDto.toString()+"\n Recepient:"+recepientOg);
    }
}
