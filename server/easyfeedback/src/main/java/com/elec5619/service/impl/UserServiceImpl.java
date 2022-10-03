package com.elec5619.service.impl;


import com.elec5619.pojo.User;
import com.elec5619.dao.UserMapper;
import com.elec5619.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getById(Long id){
        return userMapper.getById(id);
    }

}
