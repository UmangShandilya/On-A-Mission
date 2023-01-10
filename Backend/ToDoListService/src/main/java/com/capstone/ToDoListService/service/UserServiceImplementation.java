package com.capstone.ToDoListService.service;

import com.capstone.ToDoListService.exception.TaskAlreadyExistException;
import com.capstone.ToDoListService.exception.TaskNotFoundException;
import com.capstone.ToDoListService.exception.UserAlreadyExistException;
import com.capstone.ToDoListService.exception.UserNotFoundException;
import com.capstone.ToDoListService.rabbitMQ.MessageProducerForNotification;
import com.capstone.ToDoListService.rabbitMQ.NotificationDTO;
import com.capstone.ToDoListService.rabbitMQ.UserDTO;
import com.capstone.ToDoListService.model.Priority;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;;
import com.capstone.ToDoListService.rabbitMQ.MessageProducer;
import com.capstone.ToDoListService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.capstone.ToDoListService.model.Tasks.SEQUENCE_NAME;


@Service @Slf4j
public class UserServiceImplementation implements UserService {
    // Dependent On
    @Autowired
    UserRepository repository;

    @Autowired
    MessageProducer producer;

    @Autowired
    MessageProducerForNotification notificationProducer;

    @Autowired
    SequenceGenerator sequenceGenerator;


    @Override
    public User saveUserDetails(User user) throws UserAlreadyExistException
    {
        // Checkpoint
        if(repository.existsById(user.getEmail()))
            throw new UserAlreadyExistException();
        UserDTO userDetails = new UserDTO(user.getEmail(), user.getUserName(), user.getPassword());
        producer.send_DTO_ToQueue(userDetails);
        return  repository.insert(user);
    }

    @Override
    public String fetchUserName(String email) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        User user = repository.findById(email).get();
        return user.getUserName();
    }

    @Override
    public User updateUserName(String email, User requestUser) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        User user = repository.findById(email).get();
        String userName = requestUser.getUserName();
        user.setUserName(userName);
        return repository.save(user);
    }

    @Override
    public User saveTaskToList(String email, Tasks task) throws UserNotFoundException, TaskAlreadyExistException {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();


        // Fetching the task list
        User user = repository.findById(email).get();
        if (user.getTasks() == null)
        {
            user.setTasks(Arrays.asList(task));
        }
        else
        {
            task.setTaskID(sequenceGenerator.getSequence(SEQUENCE_NAME));
            List<Tasks> taskList = user.getTasks();
            taskList.add(task);
            user.setTasks(taskList);
        }

        NotificationDTO notification = new NotificationDTO(task.getTaskName(), "added successfully", email);
        notificationProducer.sendNotificationToQueue(notification);
        return repository.save(user);
    }

    @Override
    public Tasks updateExistingTask(String email, int taskID, Tasks tasks) throws UserNotFoundException, TaskNotFoundException
    {
        if (repository.findById(email).isEmpty())
            throw new UserNotFoundException();
        User user = repository.findById(email).get();
        List<Tasks> userTask = user.getTasks();
        for (Tasks t2 : userTask) {
            if (t2.getTaskID()==(taskID)) {
                userTask.remove(t2);
                tasks.setTaskID(taskID);
                userTask.add(tasks);
                NotificationDTO notification = new NotificationDTO(tasks.getTaskName(), "updated successfully",email);
            notificationProducer.sendNotificationToQueue(notification);
                user.setTasks(userTask);
                repository.save(user);
                return tasks;
            }
        }
        throw new TaskNotFoundException();

    }

    @Override
    public User deleteExistingTask(String email, int taskID) throws UserNotFoundException, TaskNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        // Finding and deleting the respective task
        User user = repository.findById(email).get();
        List<Tasks> taskList = user.getTasks();
        boolean isTaskAlreadyPresent = taskList.removeIf(task -> task.getTaskID()==taskID);
        NotificationDTO notification = new NotificationDTO("taskName", "deleted successfully", email);
        notificationProducer.sendNotificationToQueue(notification);
        if (!isTaskAlreadyPresent)
            throw new TaskNotFoundException();

        user.setTasks(taskList);

        return repository.save(user);
    }

    @Override
    public List<Tasks> getAllTasks(String email) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();
        // Finding and deleting the respective task
        User user = repository.findById(email).get();
        List<Tasks> taskList = user.getTasks();

        return taskList.stream().filter(tasks ->LocalDate.parse(tasks.getDueDate()).isAfter(LocalDate.now().minusDays(1))).filter(tasks -> !tasks.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public List<Tasks> fetchCompletedTask(String email) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        // Finding and deleting the respective task
        User user = repository.findById(email).get();
        List<Tasks> taskList = user.getTasks();

        return taskList.stream().filter(tasks -> tasks.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public List<Tasks> fetchUnfinishedTask(String email) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        // Finding and deleting the respective task
        User user = repository.findById(email).get();
        List<Tasks> taskList = user.getTasks();

        return taskList.stream().filter(tasks ->LocalDate.parse(tasks.getDueDate()).isBefore(LocalDate.now())).filter(tasks -> !tasks.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public Tasks changeCompletionStatus(String email, int taskID, Tasks tasks) throws UserNotFoundException
    {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        // Fetching the task list
        User user = repository.findById(email).get();
        List<Tasks> userTask = user.getTasks();

        Tasks taskToUpdate = userTask.stream().filter(task -> task.getTaskID()==taskID).findAny().get();
        userTask.remove(taskToUpdate);
        tasks.setCompleted(true);
        userTask.add(tasks);

        NotificationDTO notification = new NotificationDTO(tasks.getTaskName(), "is marked as completed", email);
        notificationProducer.sendNotificationToQueue(notification);

        user.setTasks(userTask);
        repository.save(user);
        return tasks;
    }

    @Override
    public List<Tasks> fetchTaskBasedOnPriority(String email, String priority) throws UserNotFoundException {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();

        // Fetching the task list
        User user = repository.findById(email).get();
        List<Tasks> userTask = user.getTasks();

        switch (priority)
        {
            case "HIGH" :
            {
                return userTask.stream().
                        filter(tasks ->LocalDate.parse(tasks.getDueDate()).isEqual(LocalDate.now())).
                        filter(task -> task.getChoosePriority().equals(Priority.HIGH)).
                        filter(tasks -> !tasks.isCompleted()).
                        collect(Collectors.toList());

            }
            case "MEDIUM" :
            {
                return userTask.stream().
                        filter(tasks ->LocalDate.parse(tasks.getDueDate()).isEqual(LocalDate.now())).
                        filter(task -> task.getChoosePriority().equals(Priority.MEDIUM)).
                        filter(tasks -> !tasks.isCompleted()).
                        collect(Collectors.toList());
            }
            case "LOW" :
            {
                return userTask.stream().
                        filter(tasks ->LocalDate.parse(tasks.getDueDate()).isEqual(LocalDate.now())).
                        filter(task -> task.getChoosePriority().equals(Priority.LOW)).
                        filter(tasks -> !tasks.isCompleted()).
                        collect(Collectors.toList());
            }
            default:
            {
                return null;
            }

        }
    }

}