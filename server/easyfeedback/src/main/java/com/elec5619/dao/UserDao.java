package com.elec5619.dao;

import com.elec5619.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from tbl_user where uid =#{id}")
    public User getById(Integer id);

}
