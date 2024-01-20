package com.example.main.control;
import com.example.main.service.find;
import com.example.main.service.login;
import jakarta.annotation.Resource;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
@Slf4j
@Controller
public class MainControl {
    @Autowired
    login login;
    @RequestMapping("/")
    String index(HttpServletRequest request, HttpServletResponse response,@Param("cookie") String mess){
        //实现cookie登录
        if(login.login(request.getCookies())) return "index";
        return "forward:login";
    }
    @RequestMapping("/login")
    String login(HttpServletRequest request, HttpServletResponse response, HashMap map) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username:" + username + "password:" + password);
        if (username == null || password == null) return "login";
        else {
            if (login.login(username, password)==false) {
                //如果账号密码不对则返回登录页面
                map.put("message","用户名或密码错误");
                return "login";
            }
            else
            {
                //为当前用户添加cookie
                response.addCookie(login.SetCookie(username));
                //重定向到首页
                response.sendRedirect("/");
                return null;
            }
        }
    }
    @RequestMapping("/register")
    String register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "register";
    }
}
