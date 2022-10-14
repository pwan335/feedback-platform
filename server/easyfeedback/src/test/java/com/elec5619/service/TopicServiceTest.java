package com.elec5619.service;

import com.elec5619.domain.Page;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.TopicQuery;
import com.elec5619.domain.topic.Collect;
import com.elec5619.domain.topic.Like;
import com.elec5619.domain.topic.TopicDetail;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        SimpleUser user = new SimpleUser();
        user.setEmail("948936249@qq.com");
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setTopicName(topicName);
        topicQuery.setPageNum(1);
        topicQuery.setPageSize(2);
        PageInfo<TopicDetail> topic = topicService.getByTopicName(topicQuery, user);
        System.out.println(topic);
    }

    @Test
    public void testGetByTopicId(){
        List<TopicDetail> topic = topicService.getByTopicId(2L, null);
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
    public void testGetLatestTopic(){
        SimpleUser user = new SimpleUser();
        user.setEmail("948936249@qq.com");
        Page page = new Page();
        page.setPageNum(1);
        page.setPageSize(2);
        PageInfo<TopicDetail> topicList = topicService.getLatestTopic(user, page);
        System.out.println(topicList);
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        Date date = ts;
//        System.out.println(date);
    }

    @Test
    public void testGetHotTopic(){
        Page page = new Page();
        page.setPageNum(1);
        page.setPageSize(2);
        PageInfo<TopicDetail> topicList = topicService.getHotTopic(null, page);
        System.out.println(topicList);
    }

    @Test
    public void testSaveCollect(){
        Collect collect = new Collect();
        collect.setTopicId(3L);
        collect.setUid(1L);
        collect.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean state = topicService.saveCollect(collect);
        System.out.println(state);
    }

    @Test
    public void testSaveLike(){
        Like like = new Like();
        like.setTopicId(1L);
        like.setUid(1L);
        like.setCreateTime(new Timestamp(System.currentTimeMillis()));
        System.out.println(like);
        boolean state = topicService.saveLike(like);
        System.out.println(state);
    }

//    @Test
//    public void testUploadImg(){
//        Long topicId = 2L;
//        List<String> saveUriList = new ArrayList<>();
//        saveUriList.add("file1.png");
//        saveUriList.add("file2.png");
//        boolean flag = topicService.uploadImg(topicId, saveUriList);
//        System.out.println(flag);
//    }
}
