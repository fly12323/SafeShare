package org.example.safeshare.service;

import org.example.safeshare.dao.User;

public interface UserService {

    //
    User findByUsername(String username);

    // 用户注册
    void register(String username, String password, String email);

    // 修改密码
    void updatePassword(String username, String newPassword);

}
