package com.elec5619.service;



import com.elec5619.domain.PmUser;
import com.elec5619.domain.User;
import com.elec5619.domain.req.UserListReq;

import java.util.List;

public interface UserService{
    User getUserById(String uid);
    int saveUser(User user);
    int updateUser(User user);
    List<User> getUsers(UserListReq req);
    List<User> getUsersByEmailOrTel(String mail,String tel);
    User login(String password,String key,String userType);
    PmUser getPmUser(String pid);
    int updatePmUser(PmUser pmUser);


    int  savePmUser(PmUser pmUser);
}
