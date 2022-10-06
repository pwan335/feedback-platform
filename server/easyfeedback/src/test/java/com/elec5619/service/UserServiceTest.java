package com.elec5619.service;


import com.elec5619.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetById(){
        User user = userService.getUserById("1");
        System.out.println(user);
    }

}
