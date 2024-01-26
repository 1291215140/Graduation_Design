package com.example.main.control;
import com.example.main.mapper.mapper;
import com.example.main.service.find;
import com.example.main.service.login;
import com.example.main.service.register;
import com.example.main.tool.sendmail;
import jakarta.annotation.Resource;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
@Slf4j
@Controller
public class MainControl {
    @Autowired
    login login;
    @Autowired
    mapper mapper;
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
        if (username == null || password == null) return "login";
        else {
            log.info("username:" + username + "password:" + password);
            if (login.login(username, password)==false) {
                //如果账号密码不对则返回登录页面
                map.put("message","用户名或密码错误");
                return "login";
            }
            else
            {
                Cookie cookie =login.SetCookie(username);
                if(cookie==null) return "login";
                //为当前用户添加cookie
                response.addCookie(cookie);
                Cookie ck = new Cookie("username",username);
                ck.setMaxAge(3600);
                response.addCookie(ck);
                //重定向到首页
                response.sendRedirect("/");
                return null;
            }
        }
    }
    @Autowired
    register register;


    @RequestMapping("/zhuce")
    String zhuce(HttpServletRequest request, HttpServletResponse response) {
        return "register";
    }
    @ResponseBody
    @PostMapping("/register")
    HashMap register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap map = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String email = request.getParameter("email");
        log.info("username:" + username + "password:" + password+"code:" + code);
        if(username == null || password == null||code == null||email == null) map.put("status","100");
        else
        {
            //字符串转换成整数
            int code1 = Integer.parseInt(code);
            if(code1!=sendmail.map.get(email)) map.put("status","400");
            else
            {
                if(register.register(username, password, email)) map.put("status","200");
                else map.put("status","300");
                sendmail.map.remove(username);
            }

        }
        return map;
    }
    //验证码
    @ResponseBody
    @RequestMapping("/sendCode")
    HashMap code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap map = new HashMap<>();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        //如果传来的find是有参数的，说明是找回页面传来，如果是无参数，说明是注册页面传来的
        String find = request.getParameter("find");
        if (email == null) map.put("status","100");
        else
        {
            //生成验证码
            String code = "";
            for(int i = 0; i < 5; i++)
            {
                code += (int) (Math.random() * 10);
            }
            //获取发送邮件对象
            sendmail ss = new sendmail();
            log.info("code"+code);
            if (find!=null){
                if(username!=null) map.put("status","100");//传来了意外的参数
                else
                {
                    map.put("email",email);
                    username = mapper.selectemail(map);
                    if(username==null) map.put("status","300");
                    else {
                        try {
                            if(ss.sendMail(email, "验证码", "" + code))
                            {
                                map.put("status","200");
                                sendmail.map.put(email, Integer.parseInt(code));
                                log.info("验证码:" + code);
                            }
                            else map.put("status","400");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            else
            {
                map.put("username",username);
                if(mapper.selectpassword(map)!=null) map.put("status","300");
                else {
                    try {
                        if (ss.sendMail(email, "验证码", "" + code))
                            {
                            map.put("status", "200");
                            sendmail.map.put(email, Integer.parseInt(code));
                            log.info("验证码:" + code);
                            }
                        else map.put("status", "300");
                        }
                    catch (MessagingException e) {
                        map.put("status", "400");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return map;
    }
    @RequestMapping("/forgetpassword")
    String forgetpassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "forgetpassword";
    }
    @ResponseBody
    @RequestMapping("/setpassword")
    HashMap findpassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap map = new HashMap<>();
        String code = request.getParameter("code");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("code:" + code + "email:" + email + "password:" + password);
        if (code == null || email == null || password == null) map.put("status","100");
        else
        {
            //字符串转换成整数
            int code1 = Integer.parseInt(code);
            if(code1!=sendmail.map.get(email)) map.put("status","300");
            else
            {
                sendmail.map.remove(email);
                map.put("email",email);
                map.put("password",password);
                if(mapper.updatepassword(map)) map.put("status","200");
                else {
                    map.put("status","400");
                }
            }
        }
        return map;
    }

}
