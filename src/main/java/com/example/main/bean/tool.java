package com.example.main.bean;
import org.springframework.context.annotation.Bean;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class tool {
    @Bean
    //获取系统当前时间，并返回格式为yyyy-MM-dd HH:mm:ss的字符串
    public String getnowtime(){
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var dateString = formatter.format(date);
        return dateString;
    }
    public String md5(String username)
    {
        //根据username生成一段utf-8字符串
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(username.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toHexString((b & 0xff) | 0x100), 1, 3);
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
