package com.capstone.AuthenticationService;

import com.capstone.AuthenticationService.exception.UserNotFoundException;
import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceTesting {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private com.capstone.AuthenticationService.service.AuthenticationServiceImplementation userService;
    private User user,user2;
    List<User> userList;
    @BeforeEach
    void setUp(){
        user=new User("sai@gmail.com","Sai","sai@123456");
        user2=new User("dff@gmail.com","dff","dff@123");
        userList= Arrays.asList(user,user2);

    }
    @AfterEach
    public void tearUp(){

        userList =null;
        user=null;
        user2=null;
    }

        //    @Test
//    public void givenUserToSaveReturnFalse() throws Exception {
//        when(userRepository.findById(user.getEmail())).thenThrow(UserAlreadyFoundException.class);
//        assertThrows(UserAlreadyFoundException.class,()->userService.saveUser(user));
//        verify(userRepository,times(0)).save(any());
//        verify(userRepository,times(1)).findById(any());
//    }

    @Test
    public void getUserByEmailAndPasswordTrue() throws UserNotFoundException {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);
        userRepository.save(user);
        assertEquals(user.getEmail(),userService.logInCheck("sai@gmail.com","sai@123456").getEmail());
        verify(userRepository,times(1)).findByEmailAndPassword(any(),any());

    }
    @Test
    public void getUserByEmailAndPasswordFalse() throws UserNotFoundException {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);
        userRepository.save(user);
        assertNotEquals(user2.getEmail(),userService.logInCheck("sai@gmail.com","sai@123456").getEmail());
        verify(userRepository,times(1)).findByEmailAndPassword(any(),any());

    }
}