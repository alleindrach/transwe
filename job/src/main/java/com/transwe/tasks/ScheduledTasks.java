package com.transwe.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{
    private static final Logger logger = LogManager.getLogger();
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        logger.info ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron(){
        logger.info ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }

}