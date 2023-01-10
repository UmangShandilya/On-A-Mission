package com.capstone.AlertService.service;

import com.capstone.AlertService.model.Notification;
import com.capstone.AlertService.model.UserNotification;

import java.util.List;

public interface NotificationService
{
    // This method saves details of notification
    UserNotification saveNotificationDetails(Notification notification, String email);

    // This method deleted notification
    List<Notification> fetchNotification(String email);

    // This method deleted notification
    UserNotification deleteNotification(String email, int notificationID);
}
