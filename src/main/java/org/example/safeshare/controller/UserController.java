package org.example.safeshare.controller;

import org.example.safeshare.dao.Result;
import org.example.safeshare.dao.User;
import org.example.safeshare.service.UserService;
import org.example.safeshare.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(String username, String password, String email) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password) && StringUtils.hasText(email)) {
            User u = userService.findByUsername(username);
            if (u == null) {
                userService.register(username,password,email);
                return Result.success();
            }else {
                return Result.error("用户名已被占用");
            }
        }else {
            return Result.error("参数不合法");
        }
    }

    @PostMapping("/login")
    public Result login(String username, String password) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            User loginUser = userService.findByUsername(username);
            // 判断用户是否存在
            if (loginUser == null) {
                return Result.error("用户名错误");
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final boolean matches = encoder.matches(password, loginUser.getPassword());
            if (matches) {
                // 登录成功 生成token
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", loginUser.getId());
                claims.put("username", loginUser.getUsername());
                String token = JwtUtil.genToken(claims);

                // 先清除redis里面的当前用户的jwt，防止多处同时登录
                Set<String> keys = stringRedisTemplate.keys("*"); // 使用KEYS *命令查找所有键
                for (String key : keys) {
                    // 解密token
                    Map<String, Object> parsedClaims = JwtUtil.parseToken(key);
                    //System.out.println(parsedClaims.get("id").getClass().getName());
                    //System.out.println(loginUser.getId().getClass().getName());
                    if (parsedClaims.containsKey("id") &&
                            Long.valueOf(parsedClaims.get("id").toString()).equals(loginUser.getId())) {
                        stringRedisTemplate.delete(key); // 删除与同一ID相关的token
                    }
                }
                // 将token存入redis
                stringRedisTemplate.opsForValue().set(token, token, 1, TimeUnit.HOURS);
                System.out.println(token);
                return Result.success(token);
            }
            return Result.error("密码错误");
        }
        return Result.error("参数不合法");
    }

    @PostMapping("/upPa")
    public Result upPa(String oldPassword, String password, String rePassword,
                       @RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(oldPassword);
        if (StringUtils.hasText(oldPassword) && StringUtils.hasText(password) && StringUtils.hasText(rePassword)) {
            if (password.equals(rePassword)) {
                Map<String, Object> parsedClaims = JwtUtil.parseToken(authorizationHeader);
                String username = parsedClaims.get("username").toString();
                User u = userService.findByUsername(username);
                if (u != null) {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    final boolean matches = encoder.matches(oldPassword, u.getPassword());
                    if (matches) {
                        userService.updatePassword(username,password);
                        return Result.success();
                    }else {
                        return Result.error("旧密码错误");
                    }
                }else {
                    return Result.error("有问题(没有该用户，你为什么要更新密码)");
                }
            }else {
                return Result.error("两次密码不一致");
            }
        }else {
            return Result.error("参数不合法");
        }
    }


}