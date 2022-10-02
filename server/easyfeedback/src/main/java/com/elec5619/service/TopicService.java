package com.elec5619.service;

import com.elec5619.pojo.Topic;
import com.elec5619.pojo.query.Collect;
import com.elec5619.pojo.query.Like;
import com.elec5619.pojo.query.TopicDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TopicService {

    /**
     * 按topicName进行话题搜索
     * @param topicName
     * @return
     */
    List<TopicDetail> getByTopicName(String topicName);

    /**
     * 查询该用户是否收藏指定话题
     * @param topicId
     * @param uid
     * @return
     */
    Collect collectedByUid(Integer topicId, Integer uid);

    /**
     * 查询该用户是否点赞指定话题
     * @param topicId
     * @param uid
     * @return
     */
    Like likedByUid(Integer topicId, Integer uid);

    /**
     * 获取根据最新时间排序的话题列表
     * @return
     */
    List<TopicDetail> getLatestTopic();

    List<TopicDetail> getHotTopic();
}
