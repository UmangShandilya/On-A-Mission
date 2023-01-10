package com.capstone.AlertService;

import com.capstone.AlertService.controller.NotificationController;
import com.capstone.AlertService.model.Notification;
import com.capstone.AlertService.model.UserNotification;
import com.capstone.AlertService.service.NotificationService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ControllerTesting {
    @InjectMocks
    NotificationController controller;
    @Autowired
    MockMvc mockMvc;
    @Mock
    NotificationService service;

    Notification notification;
    UserNotification userNotification;

    @BeforeEach
    public void set(){
        notification=new Notification(13,"project","added successfully");
        userNotification=new UserNotification("zz",List.of(notification));
        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }
    @AfterEach
    public void clear(){
        notification=null;
    }
    @Test
    public void fetchTasks() throws Exception {
        when(service.fetchNotification(anyString())).thenReturn(List.of(notification));
        mockMvc.perform(MockMvcRequestBuilders.get("/notification/fetch/zz@gmail.com").contentType(MediaType.APPLICATION_JSON).content(jsontoString(notification))).andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
        verify(service,times(1)).fetchNotification(anyString());
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
}
