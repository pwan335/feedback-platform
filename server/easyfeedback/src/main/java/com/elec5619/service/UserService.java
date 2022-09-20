package com.elec5619.service;

import com.elec5619.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    /**
     * 按id查询用户信息
     * @param id
     * @return
     */
    public User getById(Integer id);
}
