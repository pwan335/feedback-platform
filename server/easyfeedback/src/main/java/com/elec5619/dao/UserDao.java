package com.elec5619.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

}
