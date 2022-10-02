package com.elec5619.dao;

import com.elec5619.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getById(Integer id);

}
