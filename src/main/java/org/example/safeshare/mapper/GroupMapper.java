package org.example.safeshare.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.safeshare.dao.Group;

@Mapper
public interface GroupMapper {

    @Select("select * from `groups`")
    Group findByName(String name);

    @Insert("INSERT INTO `groups` (name, description, admin_id) VALUES (#{name}, #{description}, #{admin_id})")
    void newGroup(String name, String description, Long admin_id);

    @Insert("INSERT INTO group_applications (user_id, group_id, message, status) VALUES (#{userId}, #{groupId}, #{message}, 'PENDING')")
    void applyToGroup(Long userId, Long groupId, String message);
}
