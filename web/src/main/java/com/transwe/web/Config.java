package com.transwe.web;

import com.transwe.web.mvc.messagecovertor.JavaSerializationConverter;
import com.transwe.web.mvc.viewresolver.MarshallingXmlViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.time.Duration;
import java.util.List;

//https://www.zybuluo.com/javazjm/note/827060
//http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-11-17-Spring-boot%E4%B8%8EHttpMessageConverter.html
//@EnableRedisHttpSession
@Configuration
@EnableWebMvc
public class Config extends WebMvcConfigurerAdapter {
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


    /**
     * Setup a simple strategy:
     *  1. Only path extension taken into account, Accept headers ignored.
     *  2. Return HTML by default when not sure.
     */
    @Override
    public void configureContentNegotiation
    (ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                .favorPathExtension(true)

        ;

    }

    /**
     * Create the CNVR. Get Spring to inject the ContentNegotiationManager
     * created by the configurer (see previous method).
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver =
                new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

    @Bean(name = "marshallingXmlViewResolver")
    public ViewResolver getMarshallingXmlViewResolver() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // Define the classes to be marshalled - these must have
        // @Xml... annotations on them
//        marshaller.setClassesToBeBound(Account.class,
//                Transaction.class, Customer.class);
        return new MarshallingXmlViewResolver(marshaller);
    }

    // 添加converter的第二种方式
    // 通常在只有一个自定义WebMvcConfigurerAdapter时，会把这个方法里面添加的converter(s)依次放在最高优先级（List的头部）
    // 虽然第一种方式的代码先执行，但是bean的添加比这种方式晚，所以方式二的优先级 大于 方式一
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // add方法可以指定顺序，有多个自定义的WebMvcConfigurerAdapter时，可以改变相互之间的顺序
        // 但是都在springmvc内置的converter前面
        converters.add(new JavaSerializationConverter());
    }

}