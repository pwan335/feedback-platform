package com.elec5619.service;

import com.elec5619.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    /**
     * 按id查询用户信息
     * @param id
     * @return
     */
    User getById(Integer id);
}
