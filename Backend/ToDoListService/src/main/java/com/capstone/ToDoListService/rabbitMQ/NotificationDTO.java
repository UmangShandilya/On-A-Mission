package com.capstone.ToDoListService.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO
{
    private String taskName;
    private String message;
    private String email;
}
