package com.example.main.tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class tool {
    //获取系统当前时间
    public String gettime(){
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }
    public String md5(String username)
    {
        //根据username生成一段utf-8字符串
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(username.getBytes("utf-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toHexString((b & 0xff) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public HashMap loadfile(String url)
    {
        byte[] set = new byte[0];
        try {
            set = tool.class.getClassLoader().getResource(url).openStream().readAllBytes();
        } catch (IOException e) {
            return null;
        }
        char [] chars = new char[set.length];
        for (int i = 0; i < set.length; i++) {
            chars[i] = (char) set[i];
        }
        HashMap map = new HashMap<>();
        String key = "";
        String value = "";
        int i = 0;
        while(i<chars.length) {
            while(i<chars.length&&chars[i]!='=') key+=chars[i++];
            //跳过=
            i++;
            while(i<chars.length&&chars[i]!=13&&chars[i]!=10) value+=chars[i++];
            System.out.println(key+"="+value);
            map.put(key,value);
            key = "";
            value = "";
            i++;
            i++;
        }
        return map;
    }
}
