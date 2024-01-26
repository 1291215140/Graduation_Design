package com.example.main.service;

import com.example.main.tool.tool;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Slf4j
@Service
public class login {
    @Autowired
    com.example.main.mapper.mapper mapper;
    //根据账号和密码查询用户是否正确
    public boolean login(String un, String ps) {
        HashMap map = new HashMap();
        map.put("username", un);
        String password = mapper.selectpassword(map);
        if(password==null||password.equals("")) return false;
        else {
            if(password.compareTo(ps)==0) return true;
            return false;
        }
    }
    //根据cookie查询用户是否正确
    public boolean login(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) return false;
        String cookie = null;
        String username = null;
        for(int i=0;i<cookies.length;i++) {
            if(cookie!= null&&username!=null) {
                break;
            }
            if(cookies[i].getName().equals("cookie")) {
                cookie = cookies[i].getValue();
            }
            if(cookies[i].getName().equals("username")) {
                username = cookies[i].getValue();
            }
        }
        if(cookie==null||cookie.equals("")) return false;
        else
        {
            HashMap map = new HashMap();
            map.put("cookie", cookie);
            if(mapper.selectcookie(map).equals(username)) return true;
            return false;
        }
    }
    //设置cookie
    public Cookie SetCookie(String username) {
        tool tool = new tool();
        String cookie = tool.md5(username);
        HashMap map = new HashMap();
        map.put("username",username);
        map.put("cookie",cookie);
        if(mapper.updatecookie(map)==false) return null;
        Cookie cookie1 = new Cookie("cookie",cookie);
        //设置cookie的内容
        cookie1.setMaxAge(100000);
        return cookie1;
    }

}
