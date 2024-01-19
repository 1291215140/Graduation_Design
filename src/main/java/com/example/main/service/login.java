package com.example.main.service;

import com.example.main.mapper.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class login {
    @Autowired
    com.example.main.mapper.mapper mapper;
    //根据账号和密码查询用户是否正确
    public boolean login(String id, String pw) {
        return false;
    }

}
