package org.example.safeshare.service;

import org.example.safeshare.dao.Group;

public interface GroupService {

    Group findByName(String name);

    // 新建组
    void newGroup(String name, String description, Long admin_id);

    // 用户申请
    void applyToGroup(Long userId, Long groupId, String message);
}
