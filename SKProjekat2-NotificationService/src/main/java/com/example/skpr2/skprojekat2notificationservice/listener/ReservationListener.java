package com.example.skpr2.skprojekat2notificationservice.listener;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.dto.ReservationUserDto;
import com.example.skpr2.skprojekat2notificationservice.dto.UserDto;
import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import com.example.skpr2.skprojekat2notificationservice.service.impl.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

@Component
public class ReservationListener {
    private MessageHelper messageHelper;
    private EmailService emailService;
    private NotificationService notificationService;


    public ReservationListener(MessageHelper messageHelper, EmailService emailService, NotificationService notificationService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "${destination.reservationNotify}", concurrency = "5-10")
    public void reservationNotify(Message message) throws JMSException {

        ReservationUserDto reservationUserDto = messageHelper.getMessage(message, ReservationUserDto.class);
        UserDto userDto = reservationUserDto.getUserDto();

        Notification notification = notificationService.getReservationNotification(reservationUserDto);

        List<UserDto> managers = notificationService.getManagers();
        managers.forEach(m->{
            Notification n = new Notification();
            n.setDate(notification.getDate());
            n.setNotificationType(notification.getNotificationType());
            n.setText("\nMessage:\n"+notification.getText()+ "\nRecepient: "+m.getEmail());
            n.setEmail(m.getEmail());
            n.setUserID(m.getId());
            n.setId(null);

            emailService.sendSimpleMessage("mjovetic16@raf.rs", "[Auto user notification] Confirm Reservation", "\nMessage:\n"+notification.getText()+ "\n\n User just received email\nRecepient: "+m.getEmail());

            notificationService.saveNotification(n);
        });

        emailService.sendSimpleMessage("mjovetic16@raf.rs", "Confirm Reservation", notification.getText());
    }

    @JmsListener(destination = "${destination.cancelReservationNotify}", concurrency = "5-10")
    public void cancelReservationNotify(Message message) throws JMSException {

        ReservationUserDto reservationUserDto = messageHelper.getMessage(message, ReservationUserDto.class);
        UserDto userDto = reservationUserDto.getUserDto();

        Notification notification = notificationService.getCanceledReservationNotification(reservationUserDto);

        List<UserDto> managers = notificationService.getManagers();
        managers.forEach(m->{
            Notification n = new Notification();
            n.setDate(notification.getDate());
            n.setNotificationType(notification.getNotificationType());
            n.setText("\nMessage:\n"+notification.getText()+ "\nRecepient: "+m.getEmail());
            n.setEmail(m.getEmail());
            n.setUserID(m.getId());
            n.setId(null);


            emailService.sendSimpleMessage("mjovetic16@raf.rs", "[Auto user notification] Canceled Reservation", "\nMessage:\n"+notification.getText()+ "\n\n User just received email\nRecepient: "+m.getEmail());


            notificationService.saveNotification(n);

        });

        emailService.sendSimpleMessage("mjovetic16@raf.rs", "Canceled Reservation", notification.getText());
    }

    @JmsListener(destination = "${destination.reminderNotify}",concurrency = "5-10")
    public void remindNotify(Message message) throws JMSException{
        ReservationUserDto reservationUserDto = messageHelper.getMessage(message, ReservationUserDto.class);
        UserDto userDto = reservationUserDto.getUserDto();

        Notification notification = notificationService.getReminderNotification(reservationUserDto);

        List<UserDto> managers = notificationService.getManagers();
        managers.forEach(m->{

            Notification n = new Notification();
            n.setDate(notification.getDate());
            n.setNotificationType(notification.getNotificationType());
            n.setText("\nMessage:\n"+notification.getText()+ "\nRecepient: "+m.getEmail());
            n.setEmail(m.getEmail());
            n.setUserID(m.getId());
            n.setId(null);

            emailService.sendSimpleMessage("mjovetic16@raf.rs", "[Auto user notification] Reservation Reminder", "\nMessage:\n"+notification.getText()+ "\n\n User just received email\nRecepient: "+m.getEmail());

            notificationService.saveNotification(n);

        });

        emailService.sendSimpleMessage("mjovetic16@raf.rs", "Reservation Reminder", notification.getText());
    }
}
