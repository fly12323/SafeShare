package org.example.safeshare.controller;

import org.example.safeshare.dao.Group;
import org.example.safeshare.dao.Result;
import org.example.safeshare.mapper.GroupMapper;
import org.example.safeshare.service.GroupService;
import org.example.safeshare.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/newgroup")
    public Result createGroup(String name, String description,
                              @RequestHeader("Authorization") String authorizationHeader) {
        if (name != null && description != null) {
            Group group = groupService.findByName(name);
            if (group == null) {
                Map<String, Object> parsedClaims = JwtUtil.parseToken(authorizationHeader);
                Long admin_id = Long.valueOf(parsedClaims.get("id").toString());
                groupService.newGroup(name, description, admin_id);
                return Result.success();
            }else {
                return Result.error("该组已经存在");
            }
        }else {
            return Result.error("输入不合法");
        }
    }

}
