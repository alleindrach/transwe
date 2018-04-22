package com.transwe.message.redis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class Receiver {
    private static final Logger logger = LogManager.getLogger();

    public void receiveMessage(String message) {
        logger.info("====== Received <" + message + "> by redis======");
    }
}