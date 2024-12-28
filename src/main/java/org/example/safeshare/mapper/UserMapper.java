package org.example.safeshare.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.safeshare.dao.User;

@Mapper
public interface UserMapper {

    @Select("select * from users where username like #{username}")
    User findByUsername(String username);

    @Insert("insert into users (username,password,email,role) values (#{username},#{password},#{email},'USER')")
    void add(String username, String password, String email);

    @Update("update users set password = #{newPassword} where username = #{username}")
    void updatePassword(String username, String newPassword);

}
