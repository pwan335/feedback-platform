package com.elec5619.service;

import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.user.AuthUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
public class PmServiceTest {
    @Autowired
    private PmService pmService;

//    @Test
//    public void testGetById(){
//        ProductManager pm = pmService.getById(1L);
//        System.out.println(user);
//    }

    @Test
    public void testValidUserLogin(){
        SimpleUser user = new SimpleUser();
        user.setRole("pm");
        user.setEmail("948936249@qq.com");
        user.setPassword("abc123");
        boolean flag = pmService.validPmLogin(user);
        System.out.println(flag);
    }

    @Test
    public void testSaveTopic(){
        Topic topic = new Topic();
        topic.setPmId(3L);
        topic.setTopicName("save topic test");
        topic.setContent("test is successful");
        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Long topicId = pmService.saveTopic(topic);
        System.out.println(topicId);
    }

    @Test
    public void testUpdateProfile(){
        ProductManager pm = new ProductManager();
        pm.setPmId(1L);
        pm.setPmName("newPm");
        pm.setAddress("Guangdong");
        pm.setPhoneNumber("123123123");
        pm.setHobby("打篮球、跑步");
        pm.setCompany("company1");
        pm.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = pmService.updateProfile(pm);
        System.out.println(flag);
    }

}
