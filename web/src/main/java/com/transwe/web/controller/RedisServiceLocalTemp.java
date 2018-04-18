package com.transwe.web.controller;

import com.transwe.entity.user.AccessAuthEntity;
import com.transwe.entity.user.UserEntity;
import com.google.common.collect.Maps;

import java.util.Map;

public class RedisServiceLocalTemp {

    public static Map<String, AccessAuthEntity> accessAuthMap = Maps.newHashMap();

    public static Map<String, UserEntity> userMap = Maps.newHashMap();
}
