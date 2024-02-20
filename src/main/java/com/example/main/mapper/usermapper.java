package com.example.main.mapper;

import com.example.main.data.user;
import org.apache.ibatis.annotations.*;
@Mapper
public interface usermapper {

    @Select("select password from user where username = #{username}")
    String selectpassword(user user);
    @Select("select username from user where email = #{email}")
    String selectemail(user user);
    @Select("select username from user where cookie = #{cookie}")
    String selectcookie(user user);
    @Update("update user set password = #{password} where email = #{email}")
    boolean updatepassword(user user);
    @Update("update user set cookie = #{cookie} where username = #{username}")
    boolean updatecookie(user user);
    @Insert("insert into `user` (username,`password`,email) VALUES (#{username},#{password},#{email})")
    boolean insertuser(user user);
}
