package com.elec5619.service;

import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Test
    public void testGetByTopicName(){
        //接收参数
        String topicName = "study";
        //处理参数
        topicName = "%" + topicName + "%";

        List<TopicDetail> topic = topicService.getByTopicName(topicName);
        System.out.println(topic);
    }

    @Test
    public void testCollectedByUid(){
        Collect collect = topicService.collectedByUid(2L, 1L);
        boolean collected = collect != null ? true : false;
        System.out.println(collect);
        System.out.println(collected);
    }

    @Test
    public void testLikedByUid(){
        Like like = topicService.likedByUid(2L, 1L);
        boolean collected = like != null ? true : false;
        System.out.println(collected);
    }

    @Test
    public void TestGetLatestTopic(){
        List<TopicDetail> topicList = topicService.getLatestTopic();
        System.out.println(topicList);
    }

    @Test
    public void TestGetHotTopic(){
        List<TopicDetail> topicList = topicService.getHotTopic();
        System.out.println(topicList);
    }

    @Test
    public void TestSaveCollect(){
        Collect collect = new Collect();
        collect.setTopicId(3L);
        collect.setUid(1L);
        collect.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean state = topicService.saveCollect(collect);
        System.out.println(state);
    }

    @Test
    public void TestSaveLike(){
        Like like = new Like();
        like.setTopicId(3L);
        like.setUid(1L);
        like.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean state = topicService.saveLike(like);
        System.out.println(state);
    }
}
