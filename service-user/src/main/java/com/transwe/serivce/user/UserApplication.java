package com.transwe.serivce.user;

import com.transwe.properties.RedisConnectProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication

public class UserApplication  extends SpringBootServletInitializer {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private RedisConnectProperties redisConnectProperties;

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@Bean
	public TransactionTemplate transactionTemplate()
	{
		TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
		return transactionTemplate;
	}
	@Bean
	public RedisConnectProperties properties()
	{
		RedisConnectProperties redisConnectProperties=new RedisConnectProperties();
		return redisConnectProperties;
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UserApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
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

}
