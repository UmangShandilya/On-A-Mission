package com.capstone.AlertService.service;

import com.capstone.AlertService.model.Notification;
import com.capstone.AlertService.model.UserNotification;
import com.capstone.AlertService.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.capstone.AlertService.model.Notification.SEQUENCE_NAME_FOR_NOTIFICATION;

@Service
public class NotificationServiceImplementation implements NotificationService
{
    // Dependent Om
    @Autowired
    NotificationRepository repository;

    @Autowired
    SequenceGenerator sequenceGenerator;
    @Override
    public UserNotification saveNotificationDetails(Notification notification, String email)
    {
        // Checkpoint
        if(repository.existsById(email))
        {
           UserNotification user =  repository.findById(email).get();
            if (user.getNotifications() == null)
            {
                user.setNotifications(Arrays.asList(notification));
            }
            else
            {
                notification.setNotificationID(sequenceGenerator.getSequence(SEQUENCE_NAME_FOR_NOTIFICATION));
                List<Notification> notificationList = user.getNotifications();
                notificationList.add(notification);
                user.setNotifications(notificationList);
            }
            return repository.save(user);
        }
        else
        {
            List<Notification> notificationList = new ArrayList<>();
            notificationList.add(notification);
            UserNotification userNotification = new UserNotification(email,notificationList);
            return repository.insert(userNotification);
        }
    }

    @Override
    public List<Notification> fetchNotification(String email)
    {
        return repository.findById(email).get().getNotifications();
    }

    @Override
    public UserNotification deleteNotification(String email, int notificationID)
    {
        UserNotification user =  repository.findById(email).get();
        List<Notification> notificationList = user.getNotifications();
        boolean isRemoved = notificationList.removeIf(notification -> notification.getNotificationID()==notificationID);
        user.setNotifications(notificationList);
        return repository.save(user);
    }
}
