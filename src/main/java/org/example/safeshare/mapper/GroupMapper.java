package org.example.safeshare.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.safeshare.dao.Group;
import org.example.safeshare.dao.GroupApplication;

import java.util.List;

@Mapper
public interface GroupMapper {

    @Select("select * from `groups` where name like #{name}")
    Group findByName(String name);

    @Insert("INSERT INTO `groups` (name, description, admin_id) VALUES (#{name}, #{description}, #{admin_id})")
    void newGroup(String name, String description, Long admin_id);

    @Insert("INSERT INTO group_applications (user_id, group_id, message, status) VALUES (#{userId}, #{groupId}, #{message}, 'PENDING')")
    void applyToGroup(Long userId, Long groupId, String message);

    @Select("select * from `groups` where admin_id like #{admin_id}")
    List<Group> findGroupByAdminId(Long admin_id);

    // 根据管理员ID查询所有待审批的申请
    @Select("select * from group_applications where group_id like #{groupId} and status like 'PENDING'")
    List<GroupApplication> findPendingByGroups(Long groupId);

}
