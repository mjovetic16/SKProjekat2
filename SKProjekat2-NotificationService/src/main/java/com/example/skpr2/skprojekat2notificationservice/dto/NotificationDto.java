package com.example.skpr2.skprojekat2notificationservice.dto;

import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

public class NotificationDto {

    private Long id;

    private NotificationTypeDto notificationType;

    private String email;

    private String text;

    private Long userID;

    private Date date;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationTypeDto getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypeDto notificationType) {
        this.notificationType = notificationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
