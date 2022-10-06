package com.elec5619.service.impl;



import com.elec5619.dao.UserDao;
import com.elec5619.domain.PmUser;
import com.elec5619.domain.User;
import com.elec5619.domain.req.UserListReq;
import com.elec5619.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String uid) {
        return userDao.getByUid(uid);
    }

    @Override
    public int saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.modifyUser(user);
    }

    @Override
    public List<User> getUsers(UserListReq req) {
        return userDao.getUsers(req);
    }

    @Override
    public List<User> getUsersByEmailOrTel(String mail, String tel) {
        return userDao.getUsersByEmailOrTel(mail,tel);
    }

    @Override
    public User login(String password, String key,String userType) {
        return userDao.userLogin(password,key,userType);
    }

    @Override
    public PmUser getPmUser(String pid) {
        return userDao.getPmUserById(pid);
    }

    @Override
    public int updatePmUser(PmUser pmUser) {
        return userDao.updatePmUser(pmUser);
    }

    @Override
    public int savePmUser(PmUser pmUser) {
        return userDao.savePmUser(pmUser);
    }
}
