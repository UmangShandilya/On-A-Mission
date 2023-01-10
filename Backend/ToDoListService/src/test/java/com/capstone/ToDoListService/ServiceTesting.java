package com.capstone.ToDoListService;

import com.capstone.ToDoListService.exception.TaskAlreadyExistException;
import com.capstone.ToDoListService.exception.TaskNotFoundException;
import com.capstone.ToDoListService.exception.UserAlreadyExistException;
import com.capstone.ToDoListService.exception.UserNotFoundException;
import com.capstone.ToDoListService.model.Priority;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;
import com.capstone.ToDoListService.rabbitMQ.MessageProducer;
import com.capstone.ToDoListService.repository.UserRepository;
import com.capstone.ToDoListService.service.SequenceGenerator;
import com.capstone.ToDoListService.service.UserServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTesting {
    @InjectMocks
    UserServiceImplementation service;
    @Mock
    UserRepository taskRepository;
    @Mock
    MessageProducer producer;
    @Mock
    SequenceGenerator generator;
    User user,user1;

    Tasks tasks,tasks1;
    List<Tasks> taskList = new ArrayList<>();
    ArrayList<User> userList=new ArrayList<>();
    @BeforeEach
    public void set(){
        tasks=new Tasks(10,"project","2022-12-06","Todo Tracker","SE", Priority.HIGH,false);
        tasks1=new Tasks(11,"project1","2022-11-30","Todo Tracker1","SE",Priority.HIGH,true);
        taskList.add(tasks);
        user=new User("hi@gmail.com","hai","hai@123",List.of(tasks));
        user1=new User("hi2","hai2","hi1233",List.of(tasks1));
        userList.add(user);
        userList.add(user1);
    }
    @AfterEach
    public void delete(){
        tasks=null;
        user=null;
    }
    @Test
    public void addUserSuccess() throws UserAlreadyExistException {
        when(taskRepository.existsById(user.getEmail())).thenReturn(false);
        when(service.saveUserDetails(user)).thenReturn(user);
    }


    @Test
    public void testAddTaskToListSuccess() throws Exception, UserNotFoundException, TaskAlreadyExistException {
//        when(taskRepository.findById(user.getEmail())).thenReturn(Optional.ofNullable(user));
//        System.out.println("++++++++++++++++++++++++++++++++");
//        when(service.saveTaskToList(user.getEmail(), (tasks))).thenReturn(user);
//        when(taskRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
//        System.out.println("------------------------------------------");
//        assertEquals(tasks.getTaskID(),service.saveTaskToList(user.getEmail(), tasks));
        //verify(repository,times(1)).save(any(User.class));
        when(taskRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(User.class))).thenReturn(user);
        assertEquals(tasks.getTaskID(),service.saveTaskToList(user.getEmail(), tasks));
        verify(taskRepository,times(1)).save(any(User.class));
    }
    @Test
    public void testUpdateExistingTaskSuccess() throws Exception, UserNotFoundException, TaskNotFoundException {
        when(taskRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        taskRepository.save(user);
        assertEquals(tasks.getTaskID(),service.updateExistingTask(user.getEmail(), 10,tasks));
        //verify(repository,times(1)).save(any(User.class));
    }
    @Test
    public void testDeleteExistingTaskSuccess() throws Exception, UserNotFoundException, TaskNotFoundException {
        when(taskRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(User.class))).thenReturn(user);
        assertEquals(tasks.getTaskID(),service.deleteExistingTask(anyString(),10));
        verify(taskRepository,times(1)).save(any(User.class));
    }

}
