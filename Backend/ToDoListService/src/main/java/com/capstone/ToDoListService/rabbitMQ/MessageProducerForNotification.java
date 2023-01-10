package com.capstone.ToDoListService.rabbitMQ;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerForNotification
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    // The direct exchange will route the message to a queue whose binding key matches the routing key of the message exactly.
    private DirectExchange directExchange;
    public void sendNotificationToQueue(NotificationDTO notification )
    {
        rabbitTemplate.convertAndSend(directExchange.getName(), "AlertBindKey", notification );
    }
}
