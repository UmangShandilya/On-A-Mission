package com.capstone.ToDoListService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "OOPS! Unable to find the task")
public class TaskNotFoundException extends Exception{ }
