package com.transwe.web.controller.user;

import com.transwe.entity.user.UserEntity;
import com.transwe.request.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public interface UserController {


    @GetMapping("/login")
    public UserEntity login(LoginRequest loginRequest, HttpServletResponse httpRsp, HttpSession session);

    @GetMapping("/info")
    public UserEntity  info();

    @GetMapping("/update")
    public int update(String mail,Long role);

    @GetMapping("/admin")
    public UserEntity  admin();

}
