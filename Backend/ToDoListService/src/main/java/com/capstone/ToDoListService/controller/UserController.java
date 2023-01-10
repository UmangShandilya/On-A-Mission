package com.capstone.ToDoListService.controller;

import com.capstone.ToDoListService.exception.TaskAlreadyExistException;
import com.capstone.ToDoListService.exception.TaskNotFoundException;
import com.capstone.ToDoListService.exception.UserAlreadyExistException;
import com.capstone.ToDoListService.exception.UserNotFoundException;
import com.capstone.ToDoListService.model.Priority;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;
import com.capstone.ToDoListService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo-app")
public class UserController
{
    // Dependent On
    @Autowired
    UserService service;
    @PostMapping("/register")
    public ResponseEntity<?> registration (@RequestBody User user) throws UserAlreadyExistException
    {
        return new ResponseEntity<>(service.saveUserDetails(user),HttpStatus.OK);
    }

    @GetMapping("/user-name/{email}")
    public ResponseEntity<?> getUserName(@PathVariable String email) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.fetchUserName(email), HttpStatus.OK);
    }

    @PutMapping("/user-name/update/{email}")
    public ResponseEntity<?> updateUserName(@PathVariable String email, @RequestBody User user) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.updateUserName(email, user), HttpStatus.OK);
    }

   @PostMapping("/todo-list/{email}")
    public ResponseEntity<?> saveTaskOfUser(@RequestBody Tasks tasks, @PathVariable String email) throws UserNotFoundException, TaskAlreadyExistException
   {
       return new ResponseEntity<>(service.saveTaskToList(email, tasks),HttpStatus.OK);
   }

    @DeleteMapping("/delete/{email}/{taskID}")
    public ResponseEntity<?> deleteTaskInList(@PathVariable String email, @PathVariable int taskID) throws UserNotFoundException, TaskNotFoundException
    {
        return new ResponseEntity<>(service.deleteExistingTask(email, taskID),HttpStatus.OK);
    }

    @PutMapping ("/update-task/{email}/{taskID}")
    public ResponseEntity<?> updateListOfUser(@PathVariable String email, @PathVariable int taskID, @RequestBody Tasks tasks) throws UserNotFoundException, TaskNotFoundException
    {
        return new ResponseEntity<>(service.updateExistingTask(email, taskID, tasks), HttpStatus.OK);
    }

    @GetMapping("/fetch-task/{email}")
    public ResponseEntity<?> fetchAllTasks(@PathVariable String email) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.getAllTasks(email), HttpStatus.OK);
    }

    @GetMapping("/task-completed/{email}")
    public ResponseEntity<?> fetchCompletedTask(@PathVariable String email) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.fetchCompletedTask(email), HttpStatus.OK);
    }

    @GetMapping("/task-unfinished/{email}")
    public ResponseEntity<?> fetchUnfinishedTask(@PathVariable String email) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.fetchUnfinishedTask(email), HttpStatus.OK);
    }

    @PutMapping("/completed/{email}/{taskID}")
    public ResponseEntity<?> updateCompletedStatus(@RequestBody Tasks tasks, @PathVariable String email, @PathVariable int taskID) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.changeCompletionStatus(email, taskID, tasks), HttpStatus.OK);
    }

    @GetMapping("/priority/{email}/{choosePriority}")
    public ResponseEntity<?> fetchTaskBasedOnPriority(@PathVariable String email, @PathVariable String choosePriority) throws UserNotFoundException
    {
        return new ResponseEntity<>(service.fetchTaskBasedOnPriority(email, choosePriority), HttpStatus.OK);
    }
}
