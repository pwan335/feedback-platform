package com.elec5619.service;

import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetById(){
        User user = userService.getById(1L);
        System.out.println(user);
    }

    @Test
    public void testValidUserLogin(){
        SimpleUser user = new SimpleUser();
        user.setEmail("948936249@qq.com");
        user.setPassword("abc123");
        boolean flag = userService.validUserLogin(user);
        System.out.println(flag);
    }

    @Test
    public void testGetTopicByComment(){
        SimpleUser user = new SimpleUser();
        user.setEmail("948936249@qq.com");
        user.setPassword("abc123");
        user.setRole("user");
        List<TopicDetail> topicDetailList = userService.getTopicByComment(user);
        System.out.println(topicDetailList);
    }

    @Test
    public void testVerifyOldPwd(){
        AuthUserDto userDto = new AuthUserDto();
        userDto.setEmail("948936249@qq.com");
        userDto.setPassword("abc123");
        boolean flag = userService.verifyOldPwd(userDto);
        System.out.println(flag);
    }

    @Test
    public void testChangePwd(){
        AuthUserDto userDto = new AuthUserDto();
        userDto.setEmail("948936249@qq.com");
        userDto.setPassword("abc456");
        boolean flag = userService.changePwd(userDto);
        System.out.println(flag);
    }

    @Test
    public void testForgetPwd() throws IOException {
        AuthUserDto userDto = new AuthUserDto();
        userDto.setEmail("948936249@qq.com");
        boolean flag = userService.forgetPwd(userDto);
        System.out.println(flag);
    }

}
