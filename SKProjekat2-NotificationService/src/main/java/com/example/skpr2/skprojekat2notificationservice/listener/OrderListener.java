package com.example.skpr2.skprojekat2notificationservice.listener;


import com.example.skpr2.skprojekat2notificationservice.listener.helper.MessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;



import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class OrderListener {

    private MessageHelper messageHelper;

    public OrderListener(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @JmsListener(destination = "${destination.createOrder}", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        System.out.println("Test Success");
    }
}


