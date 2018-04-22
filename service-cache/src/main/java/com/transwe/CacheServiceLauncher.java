package com.transwe;

//import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
//https://github.com/mercyblitz/blogs/blob/master/java/dubbo/Dubbo-Annotation-Driven.md

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableDubboConfiguration
@DubboComponentScan("com.transwe.service.provider")
public class CacheServiceLauncher extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CacheServiceLauncher.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(CacheServiceLauncher.class, args);
	}


//	/**
//	 * 当前应用配置
//	 */
//	@Bean("dubbo-annotation-provider")
//	public ApplicationConfig applicationConfig() {
//		ApplicationConfig applicationConfig = new ApplicationConfig();
//		applicationConfig.setName("cache-service-provider");
//		return applicationConfig;
//	}
//
//	/**
//	 * 当前连接注册中心配置
//	 */
//	@Bean("zookeeper-registry")
//	public RegistryConfig registryConfig() {
//		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress("zookeeper://192.168.2.232:2181");
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
//		protocolConfig.setPort(20881);
//		return protocolConfig;
//	}
}
