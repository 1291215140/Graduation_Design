package com.example.main.control;

import com.example.main.service.find;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Slf4j
@Controller
public class MessageControl {
    @Autowired
    com.example.main.service.find find;
    @ResponseBody
    @GetMapping("/savemessage")
    boolean save(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        String name = find.getname(cookies);
        String message = request.getParameter("message");
        log.info(name+"发来消息:"+message);
        if(message == null||name == null) return false;
        return find.savemessage(name,message);
    }
    @GetMapping("/ceshi")
    String getmessge(HttpServletRequest request, HttpServletResponse response)
    {
        return "测试环境";
    }
}
