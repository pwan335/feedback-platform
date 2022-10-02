package com.elec5619.service;

import com.elec5619.pojo.Topic;
import com.elec5619.pojo.query.Collect;
import com.elec5619.pojo.query.Like;
import com.elec5619.pojo.query.TopicDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Collect collect = topicService.collectedByUid(2, 1);
        boolean collected = collect != null ? true : false;
        System.out.println(collect);
        System.out.println(collected);
    }

    @Test
    public void testLikedByUid(){
        Like like = topicService.likedByUid(2, 1);
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
}
