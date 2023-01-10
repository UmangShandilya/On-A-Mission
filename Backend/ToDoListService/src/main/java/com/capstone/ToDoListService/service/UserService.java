package com.capstone.ToDoListService.service;

import com.capstone.ToDoListService.exception.TaskAlreadyExistException;
import com.capstone.ToDoListService.exception.TaskNotFoundException;
import com.capstone.ToDoListService.exception.UserAlreadyExistException;
import com.capstone.ToDoListService.exception.UserNotFoundException;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;
//import com.capstone.ToDoListService.rabbitmq.CommonCredentials;

import java.util.List;

public interface UserService
{
    // USER RELATED METHODS

    // This method saves details of new user
    User saveUserDetails(User user) throws UserAlreadyExistException;

    // This method will fetch username
    String fetchUserName(String email) throws UserNotFoundException;

    // This method will update username
    User updateUserName(String email, User requestUser) throws UserNotFoundException;

    // TASK RELATED METHODS

    // This method saves new task details under a user
    User saveTaskToList(String email, Tasks task) throws UserNotFoundException, TaskAlreadyExistException;

    // This method updates task details
    Tasks updateExistingTask(String email, int taskID, Tasks tasks) throws UserNotFoundException, TaskNotFoundException;

    // This method deletes task under a user
    User deleteExistingTask(String email, int taskID) throws UserNotFoundException, TaskNotFoundException;

    // This method fetch all tasks of a user
    List<Tasks> getAllTasks(String email) throws UserNotFoundException;

    // This method will fetch task with a completion status as true
    List<Tasks> fetchCompletedTask(String email) throws UserNotFoundException;

    // This method will fetch unfinished tasks
    List<Tasks> fetchUnfinishedTask(String email) throws UserNotFoundException;

    // This method will change completion status
    Tasks changeCompletionStatus(String email, int taskID, Tasks tasks) throws UserNotFoundException;

    // This method will fetch tasks based on current date and priority
    List<Tasks> fetchTaskBasedOnPriority(String email, String priority) throws UserNotFoundException;


}
