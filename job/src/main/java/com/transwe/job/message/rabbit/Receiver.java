package com.transwe.job.message.rabbit;

import com.transwe.entity.user.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {
    private static final Logger logger = LogManager.getLogger();
    @RabbitListener(queues = "allein")
    public void receiveMessage(UserEntity  message) {
        logger.info("===== Received msg<" + message + "> by rabbit =====");
    }

}