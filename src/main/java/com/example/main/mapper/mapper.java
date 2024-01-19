package com.example.main.mapper;

import com.example.main.bean.bean;
import com.example.main.bean.usermessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface mapper {
    @Select("select * from user")
    ArrayList<bean> selectall();
    @Select("select message from usermessage where username = #{username}")
    String selectmessage(usermessage message);
    @Select("update usermessage set message = #{message} where username = #{username}")
    void updatemessage(usermessage message);
    @Select("insert into usermessage values(#{username},#{message})")
    void insertmessage(usermessage message);
}
