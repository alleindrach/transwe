package com.transwe;

//import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.transwe.properties.RedisConnectProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
//@EnableDubboConfiguration
//@Configuration
@DubboComponentScan("com.transwe.service.provider")
public class BundleServiceLauncher extends SpringBootServletInitializer {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private RedisConnectProperties redisConnectProperties;

	@Autowired
	private PlatformTransactionManager platformTransactionManager;
//
//	@Value("${dubbo.registry.address}")
//	private String dubboRegisterAddress;
//
//	@Value("${dubbo.application.name}")
//	private String dubboApplicationName;
//

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
		return builder.sources(BundleServiceLauncher.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(BundleServiceLauncher.class, args);
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


//	/**
//	 * 当前应用配置
//	 */
//	@Bean("dubbo-annotation-provider")
//	public ApplicationConfig applicationConfig() {
//		ApplicationConfig applicationConfig = new ApplicationConfig();
//		applicationConfig.setName("service-bundle-provider");
//		return applicationConfig;
//	}
//
//	/**
//	 * 当前连接注册中心配置
//	 */
//	@Bean("zookeeper-registry")
//	public RegistryConfig registryConfig() {
//		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress(dubboRegisterAddress);
//		return registryConfig;
//	}
//
//	/**
//	 * 当前连接注册中心配置
//	 */
//	@Bean("dubbo")
//	public ProtocolConfig protocolConfig() {
//		ProtocolConfig protocolConfig = new ProtocolConfig();
//		protocolConfig.setName("dubbo");
//		protocolConfig.setPort(20880);
//		return protocolConfig;
//	}
}
