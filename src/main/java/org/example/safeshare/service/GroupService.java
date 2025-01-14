package org.example.safeshare.service;

import org.example.safeshare.dao.Group;
import org.example.safeshare.dao.GroupApplication;

import java.util.List;

public interface GroupService {

    Group findByName(String name);

    // 新建组
    void newGroup(String name, String description, Long admin_id);

    // 用户申请
    void applyToGroup(Long userId, Long groupId, String message);

    // 管理员获取管理组
    List<Group> findGroupByAdminId(Long admin_id);

    // 来获取待审批的申请
    List<GroupApplication> findPendingApplications(Long groupId); // 获取管理员的待审批申请


}
