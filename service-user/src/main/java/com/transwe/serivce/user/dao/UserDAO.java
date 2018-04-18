package com.transwe.serivce.user.dao;

import com.transwe.entity.user.UserEntity;
import com.transwe.request.LoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @description 用户相关DAO
 */
@Mapper
public interface UserDAO {

    UserEntity login(@Param("loginRequest") LoginRequest loginRequest);

    int updateEmail(@Param("updateReq") HashMap<String,String> updateReq);

    int updateRole(@Param("updateReq") HashMap<String,String> updateReq);
}
