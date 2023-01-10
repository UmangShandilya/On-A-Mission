package com.capstone.AlertService.controller;

import com.capstone.AlertService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController
{
    // Dependent On
    @Autowired
    NotificationService service;

    @GetMapping("/fetch/{email}")
    public ResponseEntity<?> deleteNotification(@PathVariable String email)
    {
        return new ResponseEntity<>(service.fetchNotification(email), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{email}/{notificationID}")
    public ResponseEntity<?> deleteNotification(@PathVariable String email, @PathVariable int notificationID)
    {
        return new ResponseEntity<>(service.deleteNotification(email, notificationID), HttpStatus.OK);
    }
}
