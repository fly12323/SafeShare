package org.example.safeshare.service.impl;

import org.example.safeshare.dao.Group;
import org.example.safeshare.mapper.GroupMapper;
import org.example.safeshare.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Group findByName(String name) {
        Group group = groupMapper.findByName(name);
        return group;
    }

    @Override
    public void newGroup(String name, String description, Long admin_id) {
        groupMapper.newGroup(name, description, admin_id);
    }
}
