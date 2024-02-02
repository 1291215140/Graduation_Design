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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
@Slf4j
@Controller
public class ForgetPasswordControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
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
        log.info("findpassowrd say  code:"+code+"email:"+email+"password:"+password);
        if (code == null || email == null || password == null) map.put("status","100");
        else
        {
            if(!codemap.GetValue(email).equals(code)) map.put("status","300");
            else
            {
                codemap.delete(email);
                user user = new user();
                user.setEmail(email);
                user.setPassword(password);
                if(usermapper.updatepassword(user)) map.put("status","200");
                else {
                    map.put("status","400");
                }
            }
        }
        return map;
    }
}
