package com.elec5619.dao;


import com.elec5619.domain.PmUser;
import com.elec5619.domain.User;
import com.elec5619.domain.req.UserListReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao{
  User getByUid(String uid);
  int saveUser(User user);
  int modifyUser(User user);

  List<User> getUsers(UserListReq req);
  List<User> getUsersByEmailOrTel(@Param("mail") String mail, @Param("tel") String tel);
  User userLogin(@Param("password")String password,@Param("key")String key,@Param("userType")String userType);
  PmUser getPmUserById(String pid);
  int updatePmUser(PmUser pmUser);

  int savePmUser(PmUser pmUser);
}
