package com.example.main.service;

import com.example.main.mapper.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class register{
    @Autowired
    mapper mapper;
    public boolean register(String username, String password){
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        if(mapper.selectpassword(map)!= null) return false;
        else
        {
            mapper.insertuser(map);
            return true;
        }
    }
}