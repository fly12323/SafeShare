package org.example.safeshare.service.impl;

import org.example.safeshare.dao.User;
import org.example.safeshare.mapper.UserMapper;
import org.example.safeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password, String email) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        userMapper.add(username, password, email);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newPassword = encoder.encode(newPassword);
        userMapper.updatePassword(username, newPassword);
    }

}
