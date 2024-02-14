package com.example.main.control;
import com.example.main.data.user;
import com.example.main.mapper.usermapper;
import com.example.main.service.SendEmail;
import com.example.main.service.Verification_Code_Map;
import com.example.main.bean.tool;
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
public class LoginControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
    @RequestMapping("/")
    String index(HttpServletRequest request, HttpServletResponse response,@Param("cookie") String mess){
        //实现cookie登录
        var cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        {
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
            if(cookie==null||cookie.equals(""))  return "forward:login";
            else
            {
                var user = new user();
                user.setUsername(username);
                user.setCookie(cookie);
                var ck = usermapper.selectcookie(user);
                if(ck==null||ck.equals("")) return "forward:login";
                if(usermapper.selectcookie(user).equals(username))return "index";
                return "forward:login";
            }
        }
        return "forward:login";
    }
    @RequestMapping("/login")
    String login(HttpServletRequest request, HttpServletResponse response, HashMap map) throws IOException {
        var username = request.getParameter("username");
        var password = request.getParameter("password");
        if (username == null || password == null||password.equals("")||username.equals("")) return "login";
        else {
            user user = new user();
            user.setUsername(username);
            user.setPassword(password);
            log.info("login say:"+"username:" + username + "password:" + password);
            if (!usermapper.selectpassword(user).equals(password)) {
                //如果账号密码不对则返回登录页面
                map.put("context","用户名或密码错误");
                return "login";
            }
            else
            {
                var tool = new tool();
                var cookie = tool.md5(username);
                user.setCookie(cookie);
                if(cookie==null) return "login";
                if(!usermapper.updatecookie(user)) return "login";
                //为当前用户添加cookie
                Cookie cookie1 = new Cookie("cookie",cookie);
                //设置cookie的内容
                cookie1.setMaxAge(100000);
                response.addCookie(cookie1);
                Cookie ck = new Cookie("username",username);
                ck.setMaxAge(3600);
                response.addCookie(ck);
                //重定向到首页
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }
    }


}
