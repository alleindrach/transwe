package com.transwe.service.facade.user;

import com.transwe.entity.user.UserEntity;
import com.transwe.request.LoginRequest;

public interface UserService {
    public UserEntity login(LoginRequest request);

    public int updateInfo(UserEntity ue);
}
