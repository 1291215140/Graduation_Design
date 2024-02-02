package com.example.main.control;

import com.example.main.data.user;
import com.example.main.mapper.usermapper;
import com.example.main.service.SendEmail;
import com.example.main.service.Verification_Code_Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Controller
public class RegisterControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
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
            log.info("register say code="+codemap.GetValue(email));
            if(!code.equals(codemap.GetValue(email))) map.put("status","400");
            else
            {
                user user = new user();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                if(usermapper.selectemail(user)!=null||usermapper.selectpassword(user)!=null) map.put("status","300");
                else map.put("status","200");
                usermapper.insertuser(user);
                codemap.delete(username);
            }
        }
        return map;
    }

}
