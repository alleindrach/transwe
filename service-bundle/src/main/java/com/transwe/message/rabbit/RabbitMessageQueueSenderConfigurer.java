package com.transwe.message.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

//@Configuration
//@EnableRabbit
public class RabbitMessageQueueSenderConfigurer {
    private static final Logger logger = LogManager.getLogger();


    static final String topicExchangeName = "exchange";

    static final String queueName = "allein";

    static final String routingKey="provider";
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    Queue queue() {
        Queue queue = new Queue(queueName, true);
//        rabbitAdmin.declareQueue(queue);
        queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    @Bean
    TopicExchange exchange() {

        TopicExchange topicExchange = new TopicExchange(topicExchangeName);
//        rabbitAdmin.declareExchange(topicExchange);
        topicExchange.setAdminsThatShouldDeclare(rabbitAdmin);
        return topicExchange;
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
//        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * 生产者用
     *
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
