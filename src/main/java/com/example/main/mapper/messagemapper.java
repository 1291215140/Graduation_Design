package com.example.main.mapper;

import org.apache.ibatis.annotations.*;
import java.util.HashMap;

@Mapper
public interface messagemapper {
    @Select("select message from usermessage where username = #{username}")
    String selectMessage(HashMap map);
    //插入数据
    @Insert("insert into usermessage(username,message) values(#{username},#{message}) ON DUPLICATE KEY UPDATE message = #{message}")
    boolean insertMessage(HashMap<String,String> map);
}
