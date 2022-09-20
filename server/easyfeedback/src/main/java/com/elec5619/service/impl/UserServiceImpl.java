package com.elec5619.service.impl;


import com.elec5619.dao.UserDao;
import com.elec5619.domain.User;
import com.elec5619.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Integer id){
        return userDao.getById(id);
    }

}
