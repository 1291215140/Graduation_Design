package com.example.main.mapper;

import com.example.main.bean.usermessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;

@Mapper
public interface mapper {
    @Select("select message from usermessage where username = #{username}")
    String selectmessage(usermessage message);
    @Select("update usermessage set message = #{message} where username = #{username}")
    void updatemessage(usermessage message);
    @Select("insert into usermessage values(#{username},#{message})")
    void insertmessage(usermessage message);
    @Select("select password from user where username = #{username}")
    String selectpassword(HashMap map);
    @Select("select username from user where email = #{email}")
    String selectemail(HashMap map);
    @Select("select username from user where cookie = #{cookie}")
    String selectcookie(HashMap map);
    @Update("update user set cookie = #{cookie} where username = #{username}")
    void updatecookie(HashMap map);
    @Insert("insert into `user` (username,`password`) VALUES (#{username},#{password})")
    void insertuser(HashMap map);
}
