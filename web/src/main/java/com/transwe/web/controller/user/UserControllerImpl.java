package com.transwe.web.controller.user;

import com.transwe.aop.annotation.AuthLogin;
import com.transwe.aop.annotation.AuthPermission;
import com.transwe.entity.user.RoleEntity;
import com.transwe.entity.user.UserEntity;
import com.transwe.exception.CommonException;
import com.transwe.exception.ExceptionEnum;
import com.transwe.service.facade.cache.RedisService;
import com.transwe.service.facade.user.UserService;
import com.transwe.request.LoginRequest;
import com.transwe.web.utils.KeyGenerator;
import com.transwe.web.utils.RedisPrefixUtil;
import com.transwe.web.utils.WebUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.Logger;

@RestController
public class UserControllerImpl implements UserController {

    @Reference(version = "1.0.0" ,check=false)
    private UserService userService;
    @Reference(version = "1.0.0",check=false)
    private RedisService redisService;
    private static final Logger logger = LogManager.getLogger();

    @Resource
    private WebUtils webutils;
    /** Session有效时间 */
    @Value("${session.expireTime}")
    private long sessionExpireTime;

    /** HTTP Response中Session ID 的名字 */
    @Value("${session.SessionIdName}")
    private String sessionIdName;

    @Value("${session.UserIdName}")
    private String userIdName;


    @Override
    public UserEntity login(LoginRequest request, HttpServletResponse httpRsp,HttpSession session) {
//
//        // 登录鉴权
        UserEntity userEntity = userService.login(request);
//
//        // 登录成功

        doLoginSuccess(userEntity, httpRsp,session);
//        return Result.newSuccessResult(userEntity);

        logger.info("logging|"+request.toString());

        return userEntity;
    }
    @Override
    @AuthLogin
    public UserEntity info()
    {
        return webutils.getUserIdentity();

    }

    @Override
    @AuthPermission(values={"admin"})
    public UserEntity admin()
    {
        return webutils.getUserIdentity();

    }
    private void doLoginSuccess(UserEntity userEntity, HttpServletResponse httpRsp,HttpSession session) {
        if(userEntity!=null) {
            // 生成SessionID
            String sessionID = RedisPrefixUtil.SessionID_Prefix + KeyGenerator.getKey();

            // 将 SessionID-UserEntity 存入Redis
            redisService.set(sessionID, userEntity, sessionExpireTime);
//        RedisServiceTemp.userMap.put(sessionID, userEntity);

            // 将SessionID存入HTTP响应头
            Cookie cookie = new Cookie(sessionIdName, sessionID);
            httpRsp.addCookie(cookie);
            Cookie cookieUser = new Cookie(userIdName, userEntity.getId().toString());
            httpRsp.addCookie(cookieUser);
        }
        else
        {
            throw new CommonException(ExceptionEnum.NOT_LOG_IN);
        }
    }
    @Override
    @AuthLogin
    public int update(String mail,Long role)
    {
        UserEntity ue=webutils.getUserIdentity();
        ue.setMail(mail);
        ue.setRoleEntity(new RoleEntity());
        ue.getRoleEntity().setId(role);
        int result=userService.updateInfo(ue);
        return result;
    }
}
