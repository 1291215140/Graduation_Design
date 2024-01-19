package com.example.main.bool;

public class bool {
    //获取系统当前时间
    public String gettime(){
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }
}
