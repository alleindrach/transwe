package com.transwe.message.redis;

import com.transwe.properties.RedisConnectProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisMessageQueueSenderConfigurer {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private RedisConnectProperties redisConnectProperties;

    @Bean
    public RedisConnectProperties properties()
    {
        RedisConnectProperties redisConnectProperties=new RedisConnectProperties();
        return redisConnectProperties;
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
