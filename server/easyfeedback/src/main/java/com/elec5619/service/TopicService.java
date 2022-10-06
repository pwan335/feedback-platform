package com.elec5619.service;

import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import java.util.List;

public interface TopicService{

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
    Collect collectedByUid(Long topicId, Long uid);

    /**
     * 查询该用户是否点赞指定话题
     * @param topicId
     * @param uid
     * @return
     */
    Like likedByUid(Long topicId, Long uid);

    /**
     * 获取根据最新时间排序的话题列表
     * @return
     */
    List<TopicDetail> getLatestTopic();

    /**
     * 根据点赞数、收藏数、评论数，从高到低获取热门话题
     * @return
     */
    List<TopicDetail> getHotTopic();

    /**
     * 用户点赞话题
     * @param like
     * @return
     */
    boolean saveLike(Like like);

    /**
     * 用户收藏话题
     * @param collect
     * @return
     */
    boolean saveCollect(Collect collect);
}
