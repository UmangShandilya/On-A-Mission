package com.capstone.AlertService;

import com.capstone.AlertService.model.Notification;
import com.capstone.AlertService.model.UserNotification;
import com.capstone.AlertService.repository.NotificationRepository;
import com.capstone.AlertService.service.NotificationServiceImplementation;
import com.capstone.AlertService.service.SequenceGenerator;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTesting {
    @InjectMocks
    NotificationServiceImplementation service;
    @Mock
    NotificationRepository repository;
    @Mock
    SequenceGenerator generator;

    Notification notification;
    UserNotification userNotification;
    List<UserNotification> un=new ArrayList<>();
    @BeforeEach
    public void set(){
        notification=new Notification(13,"project","added successfully");
        userNotification=new UserNotification("zz", List.of(notification));
        un.add(userNotification);
    }
    @AfterEach
    public void clear(){
        notification=null;
    }

    @Test
    public void fetchNotification(){
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(userNotification));
        assertEquals(notification, service.fetchNotification(anyString()).get(0));
       verify(repository, times(1)).findById(anyString());

    }
    @Test
    public void deleteNotification(){
        when(repository.findById(userNotification.getEmail())).thenReturn(Optional.of(userNotification));
//        when(repository.save(any())).thenReturn(userNotification);
        when(repository.save(userNotification)).thenReturn(userNotification);
//        when(repository.delete(notification.getNotificationID()));

        assertEquals(notification,service.deleteNotification(userNotification.getEmail(), 13));
        verify(repository,times(1)).findById(userNotification.getEmail());
    }
}
