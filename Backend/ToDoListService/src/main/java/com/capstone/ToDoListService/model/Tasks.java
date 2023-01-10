package com.capstone.ToDoListService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;


@Data @NoArgsConstructor @AllArgsConstructor
public class Tasks
{
    @Transient
    public static final String SEQUENCE_NAME="User_sequence";
    @Id
    private int taskID;
    private String taskName;
    private String dueDate;
    private String content, category;
    private Priority choosePriority;
    private boolean isCompleted = false;
}
