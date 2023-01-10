package com.capstone.AuthenticationService;
import com.capstone.AuthenticationService.controller.AuthController;
import com.capstone.AuthenticationService.exception.UserAlreadyExistsException;
import com.capstone.AuthenticationService.exception.UserNotFoundException;
import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.service.AuthenticationService;
import com.capstone.AuthenticationService.token.JWTGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTesting {
    @Autowired
    MockMvc mockMvc;

    @Mock
    AuthenticationService userService;
    @Mock
    JWTGenerator token;

    private User user, user2;
    List<User> userList;

    @InjectMocks
    AuthController userController;

    @BeforeEach
    void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
        user=new User("abc1@gmail.com","abc","123654");
        user2=new User("xc@gmail.com","xc","654321");
    }
    @Test
    public void givenUserToSaveReturnSaveUserSuccess() throws Exception, UserAlreadyExistsException {

        when(userService.saveUser(any())).thenReturn(user);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsontoString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());

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
    public void givenUserToSaveReturnSaveUserFailure() throws Exception, UserAlreadyExistsException {

        when(userService.saveUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsontoString(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());

    }
    @Test
    public void testUserLoginByEmailAndPasswordSuccess() throws Exception, UserNotFoundException {

        when(userService.logInCheck(anyString(),anyString())).thenReturn(user);
        when(token.generateToken(any())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsontoString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).logInCheck(anyString(),anyString());
    }

    @Test
    public void testUserLoginByEmailAndPasswordFaliure() throws Exception, UserNotFoundException {

        when(userService.logInCheck(anyString(),anyString())).thenThrow(new UserNotFoundException());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsontoString(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).logInCheck(anyString(),anyString());
    }
}
