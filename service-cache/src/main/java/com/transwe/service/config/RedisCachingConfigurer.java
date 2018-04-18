package com.transwe.service.config;

import com.transwe.properties.RedisConnectProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;


@Configuration
@EnableCaching
public class RedisCachingConfigurer extends CachingConfigurerSupport {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisConnectProperties redisConnectProperties;

    /**
     * 生产key的策略
     *
     * @return
     */

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager CacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager rcm =  RedisCacheManager.create(redisConnectionFactory);

        return (CacheManager) rcm;
    }

    /**
     * redis 数据库连接池
     * @return
     */

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

    /**
     * redisTemplate配置
     *
     * @param factory
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisConnectProperties properties()
    {
        RedisConnectProperties redisConnectProperties=new RedisConnectProperties();
        return redisConnectProperties;
    }
}
