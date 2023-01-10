package com.capstone.ToDoListService.rabbitMQ;

import org.springframework.amqp.core.*;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration
{
    // spring bean for rabbitmq queue for Authentication
    @Bean
    public Queue authenticationQueue()
    {
        return new Queue("AuthenticationDetails");
    }

    // spring bean for queue for Notification
    @Bean
    public Queue notificationQueue()
    {
        return new Queue("NotificationDetails");
    }

    // spring bean for rabbitmq exchange
    @Bean
    public DirectExchange exchange()
    {
        return new DirectExchange("RabbitExchanger");
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding authenticationBinding()
    {
        return BindingBuilder.bind(authenticationQueue()).to(exchange()).with("BindKey");
    }

    // binding between json queue and exchange using routing key
    @Bean
    public Binding notificationBinding()
    {
        return BindingBuilder.bind(notificationQueue()).to(exchange()).with("AlertBindKey");
    }

    @Bean
    public Jackson2JsonMessageConverter getProducerJackson()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getProducerJackson());
        return rabbitTemplate;
    }

}
