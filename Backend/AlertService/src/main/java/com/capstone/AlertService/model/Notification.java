package com.capstone.AlertService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Notification
{
    @Transient
    public static final String SEQUENCE_NAME_FOR_NOTIFICATION="Notification_seq";
    @Id
    private int notificationID;
    private String taskName;
    private String message;
}
