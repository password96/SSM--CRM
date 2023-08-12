package com.shw.crm.settings.service.impl;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.settings.mapper.UserMapper;
import com.shw.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        System.out.println(123);
        System.out.println(userMapper);
        return userMapper.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int saveNewUser(User user) {
        return userMapper.insertNewUser(user);
    }
}

