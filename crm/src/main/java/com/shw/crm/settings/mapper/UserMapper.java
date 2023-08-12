package com.shw.crm.settings.mapper;

import com.shw.crm.settings.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    User selectUserByLoginActAndPwd(Map<String, Object> map);
    List<User> selectAllUsers();
    int insertNewUser(User user);
}