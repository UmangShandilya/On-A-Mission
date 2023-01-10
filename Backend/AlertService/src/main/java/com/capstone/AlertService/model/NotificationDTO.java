package com.capstone.AlertService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO
{
    private int notificationID;
    private String taskName, message,email;
}
