package com.elec5619.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.UserDao;
import com.elec5619.domain.User;
import com.elec5619.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {


}
