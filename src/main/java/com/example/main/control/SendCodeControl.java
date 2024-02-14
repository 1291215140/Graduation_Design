package com.example.main.control;

import com.example.main.data.ResponseData;
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
@Slf4j
@Controller
public class SendCodeControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
    @ResponseBody
    @RequestMapping("/sendCode")
    ResponseData code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var username = request.getParameter("username");
        var email = request.getParameter("email");
        var find = request.getParameter("find");
        user user = new user();
        user.setEmail(email);
        user.setUsername(username);
        if (email == null) return new ResponseData("100");//没有邮箱
        //生成验证码
        String code = "";
        for(int i = 0; i < 5; i++)
        {
            code += (int) (Math.random() * 10);
        }
        log.info("code"+code);
        //如果传来的find是有参数的，说明是找回页面传来，如果是无参数，说明是注册页面传来的
        if (find!=null){
            if(username!=null) return new ResponseData("100");//传来了意外的参数
            if(usermapper.selectemail(user)==null) return new ResponseData("300");//邮箱不存在
            else {
                if(sendmail.SendEmail(email, "验证码", "" + code))
                {
                    codemap.PutValue(email,code);
                    log.info("SendCode Say"+"验证码:" + code);
                    return new ResponseData("200");
                }
                else return new ResponseData("400");
            }
        }
        else
        {
            if(usermapper.selectpassword(user)!=null) new ResponseData("300");
            if (sendmail.SendEmail(email, "验证码", "" + code))
            {
                codemap.PutValue(email, code);
                log.info("验证码:" + code);
                return new ResponseData("200");
            }
            else return new ResponseData("300");
        }
    }
}
