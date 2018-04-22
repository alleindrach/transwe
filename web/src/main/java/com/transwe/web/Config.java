package com.transwe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

//@EnableRedisHttpSession
public class Config {
//
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .commandTimeout(Duration.ofSeconds(2))
//                .shutdownTimeout(Duration.ZERO)
//                .build();
//
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
//    }
}