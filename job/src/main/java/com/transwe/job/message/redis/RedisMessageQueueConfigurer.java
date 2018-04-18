package com.transwe.job.message.redis;

import com.transwe.properties.RedisConnectProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

//@Configuration
public class RedisMessageQueueConfigurer {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private RedisConnectProperties redisConnectProperties;
    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public RedisConnectProperties properties()
    {
        RedisConnectProperties redisConnectProperties=new RedisConnectProperties();
        return redisConnectProperties;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            org.springframework.data.redis.listener.adapter.MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    org.springframework.data.redis.listener.adapter.MessageListenerAdapter listenerAdapter(
            Receiver receiver)
    {
        return new org.springframework.data.redis.listener.adapter.MessageListenerAdapter(receiver, "receiveMessage");
    }



    //
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        logger.info("====init connect to "+redisConnectProperties.toString()+"=======");
        JedisConnectionFactory factory = new JedisConnectionFactory(
//                new JedisShardInfo(
//                redisConn.getHost(),    redisConn.getPort(),redisConn.getTimeout()
//        )
        );

        factory.setHostName(redisConnectProperties.getHost());
        factory.setPort(redisConnectProperties.getPort());
        factory.setTimeout(redisConnectProperties.getTimeout()); // 设置连接超时时间
        return factory;
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
