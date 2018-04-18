package com.transwe.serivce.user.service;

import com.transwe.entity.user.UserEntity;
import com.transwe.exception.CommonException;
import com.transwe.exception.ExceptionEnum;
import com.transwe.serivce.user.dao.UserDAO;
import com.transwe.service.facade.user.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.transwe.request.LoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;

@Service(version = "1.0.0")
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    static final String topicExchangeName = "exchange";

    static final String queueName = "allein";

    static final String routingKey = "user";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StringRedisTemplate redisMessageTemplate;

    @Autowired
    private RabbitMessagingTemplate rabbitMessageTemplete;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public UserEntity login(LoginRequest request) {
        // 具体的实现代码
        logger.info("login|" + request.toString());
        UserEntity ue = userDAO.login(request);
        if (ue != null) {
            logger.info("user|" + ue.toString());
        }
        logger.info("===== Sending message =====");
        redisMessageTemplate.convertAndSend("chat", "Hello from redis!");
        rabbitMessageTemplete.convertAndSend(topicExchangeName, routingKey, ue);
        return ue;
    }

    @Override
//        @Transactional
    public int updateInfo(UserEntity ue) {
        logger.info("=====updating " + ue + " =====");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", ue.getId().toString());
        map.put("mail", ue.getMail());
        map.put("role", ue.getRoleEntity().getId().toString());

        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                Integer a=userDAO.updateEmail(map);
                if(a<2)
                    throw new CommonException(ExceptionEnum.NOT_LOG_IN);
                Integer b=userDAO.updateRole(map);
                return a&b;
            }
        });

    }

}
