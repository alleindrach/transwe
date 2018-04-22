package com.transwe.web.utils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.transwe.entity.user.UserEntity;
import com.transwe.exception.CommonException;
import com.transwe.exception.ExceptionEnum;
import com.transwe.service.facade.cache.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class WebUtils {
    @Value("${session.SessionIdName}")
    private String sessionIdName;
    @Value("${session.UserIdName}")
    private String userIdName;
//    @Reference(version = "1.0.0")
    private RedisService redisService;
    private static final Logger logger = LogManager.getLogger();

    public WebUtils(RedisService redisService)
    {
        redisService=redisService;
    }
    public  HttpServletRequest getRequest()
    {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
    public  UserEntity getUserIdentity()
    {
        try {
            Cookie[] cookies = getRequest().getCookies();

            String sessionId = "";

            Long userId = null;

            for (Cookie cookie : cookies) {
                if (sessionIdName.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
            UserEntity ue = (UserEntity) redisService.get(sessionId);

            return ue;
        }
        catch (Exception ex)
        {
            logger.info("userIdentity access error",ex);
            throw new CommonException(ExceptionEnum.NOT_LOG_IN);
        }
    }
}
