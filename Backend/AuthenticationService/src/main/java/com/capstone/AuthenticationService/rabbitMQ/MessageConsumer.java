package com.capstone.AuthenticationService.rabbitMQ;

import com.capstone.AuthenticationService.exception.UserAlreadyExistsException;
import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.service.AuthenticationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer
{
    @Autowired
    private AuthenticationService service;

    @RabbitListener(queues = "AuthenticationDetails")
    public void get_DTO_From_Queue(User user) throws UserAlreadyExistsException
    {
        service.saveUser(user);
        System.out.println("Auth Details Added");
    }
}
