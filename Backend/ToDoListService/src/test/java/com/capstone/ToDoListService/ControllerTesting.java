package com.capstone.ToDoListService;
import com.capstone.ToDoListService.controller.UserController;
import com.capstone.ToDoListService.exception.TaskAlreadyExistException;
import com.capstone.ToDoListService.exception.TaskNotFoundException;
import com.capstone.ToDoListService.exception.UserNotFoundException;
import com.capstone.ToDoListService.model.Priority;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;
import com.capstone.ToDoListService.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ControllerTesting {
    @InjectMocks
    UserController controller;
    @Autowired
    MockMvc mockMvc;
    @Mock
    UserService service;


    User user,user1;
    Tasks tasks,tasks1;
    @BeforeEach
    public void set(){
        tasks=new Tasks(10,"project","2022-12-06","Todo Tracker","SE",Priority.HIGH,false);
        tasks1=new Tasks(11,"project1","2022-11-30","Todo Tracker1","SE",Priority.HIGH,true);

        user=new User("hi@gmail.com","hai","hai@123",List.of(tasks,tasks1));
        user1=new User("hi2","hai2","hi1233",List.of(tasks1));
        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }
    @AfterEach
    public void delete(){
        tasks=null;
        user=null;
    }
    @Test
    public void addTaskIntoUserSuccess() throws UserNotFoundException, TaskAlreadyExistException, Exception {
        when(service.saveTaskToList("zz",tasks)).thenReturn(user);
        mockMvc.perform(
                        post("/todo-app/todo-list/zz").
                                contentType(MediaType.APPLICATION_JSON).
                                content(jsontoString(tasks))).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    private static String jsontoString(final Object o) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(o);
            result=jsonContent;
        }
        catch(JsonProcessingException e){
            result="JSON Processing error";
            ;            }
        return result;
    }
    @Test
    public void testUpdateExistingTaskSuccess() throws Exception, UserNotFoundException, TaskNotFoundException {
        when(service.updateExistingTask("zz",10,tasks)).thenReturn(tasks);
        mockMvc.perform(put("/todo-app/update-task/zz/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsontoString(tasks)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        //verify(service,times(1)).updateExistingTask(anyInt(),anyInt(),any());
    }
    @Test
    public void testDeleteExistingTask() throws Exception, UserNotFoundException, TaskNotFoundException {
        when(service.deleteExistingTask(anyString(),anyInt())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.delete("/todo-app/delete/zz/10").contentType(MediaType.APPLICATION_JSON).content(jsontoString(tasks)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void unfinishedTasks() throws UserNotFoundException, Exception {
        when(service.fetchUnfinishedTask(anyString())).thenReturn((List.of(tasks)));
        mockMvc.perform(MockMvcRequestBuilders.get("/todo-app/task-unfinished/zz").contentType(MediaType.APPLICATION_JSON).content(jsontoString(tasks))).andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
        verify(service,times(1)).fetchUnfinishedTask(anyString());
    }





}
