package com.example.main.control;

import com.example.main.service.find;
import com.example.main.service.login;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
@Slf4j
@Controller
public class MainControl {
    @Autowired
    find find;
    @Autowired
    login login;
    @GetMapping("/login")
    String login(HttpServletRequest request, HttpServletResponse response,HashMap map)
    {
        String str = request.getParameter("str");
        map.put("str",str);
        log.info(str);
        return "login";
    }
    //get和post请求都能接受
    @RequestMapping("/")
    String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username==null || password==null)
        {
            log.info("空的");
            Cookie[] cookies  = request.getCookies();
            if(cookies==null) return "redirect:login";
            else {
                find.outputcookie(cookies);
                return "index";
            }
        }
        else
        {
            String str = null;
            if(login.login(username,password))
            {
                return "index";
            }
            //log.info(name+":"+password);
            return "redirect:login?str="+str;
        }
    }
    @ResponseBody
    @GetMapping("/savemessage")
    HashMap<String,String> save(HttpServletRequest request, HttpServletResponse response)
    {
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        HashMap<String,String> map = new HashMap<>();
        log.info(name+"发来消息:"+message);
        if(name == null || message == null)
        {
            map.put("message","消息为空");
            return map;
        }
        find.savemessage(name,message);
        map.put("message","成功保存消息");
        return map;
    }
    @GetMapping("/ceshi")
    String getmessge(HttpServletRequest request, HttpServletResponse response)
    {
        return "测试环境";
    }
}
