package com.transwe.web;

//import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,SessionAutoConfiguration.class})
//@EnableDubboConfiguration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60) //1分钟失效 springSessionRepositoryFilter
@DubboComponentScan("com.transwe.web")
public class WebControllerLauncher extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WebControllerLauncher.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(WebControllerLauncher.class, args);
	}

//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//		return new EmbeddedServletContainerCustomizer() {
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer container) {
//				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
//				container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
//			}
//		};
//	}

//	/**
//	 * 当前应用配置
//	 */
//	@Bean
//	public ApplicationConfig applicationConfig() {
//		ApplicationConfig applicationConfig = new ApplicationConfig();
//		applicationConfig.setName("web-controller");
//		return applicationConfig;
//	}

//	/**
//	 * 当前连接注册中心配置
//	 */
//	@Bean
//	public RegistryConfig registryConfig() {
//		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress("zookeeper://192.168.2.232:2181");
//		return registryConfig;
//	}

//	/**
//	 * 当前连接注册中心配置
//	 */
//	@Bean
//	public ConsumerConfig consumerConfig() {
//		ConsumerConfig consumerConfig = new ConsumerConfig();
//		consumerConfig.setTimeout(3000);
//		return consumerConfig;
//	}
}
