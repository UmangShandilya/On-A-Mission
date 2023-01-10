package com.capstone.AlertService.rabbitMQ;

import com.capstone.AlertService.model.Notification;
import com.capstone.AlertService.model.NotificationDTO;
import com.capstone.AlertService.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer
{
    @Autowired
    private NotificationService service;

    @RabbitListener(queues = "NotificationDetails")
    public void get_DTO_From_Queue(NotificationDTO notification)
    {
        String email = notification.getEmail();
//        Notification newNotification = new Notification(notification.getTaskName(), notification.getMessage());
        Notification newNotification=new Notification(notification.getNotificationID(), notification.getTaskName(),notification.getMessage());
        service.saveNotificationDetails(newNotification,email);
        System.out.println("Notification Added");
    }
}