package com.elec5619.service.impl;

import com.elec5619.dao.TopicMapper;
import com.elec5619.pojo.Topic;
import com.elec5619.pojo.query.Collect;
import com.elec5619.pojo.query.Like;
import com.elec5619.pojo.query.TopicDetail;
import com.elec5619.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicMapper topicMapper;

    public Integer getCollectNumByTopicId(Integer topicId){
        return topicMapper.getCollectNumByTopicId(topicId) != null ? topicMapper.getCollectNumByTopicId(topicId) : 0;
    }

    public Integer getLikeNumByTopicId(Integer topicId){
        return topicMapper.getLikeNumByTopicId(topicId) != null ? topicMapper.getLikeNumByTopicId(topicId) : 0;
    }

    public Integer getCommentNum(Integer topicId){
        return topicMapper.getCommentNum(topicId) != null ? topicMapper.getCommentNum(topicId) : 0;
    }

    public List<TopicDetail> getByTopicName(String topicName){
        topicName = "%" + topicName + "%";
        List<TopicDetail> topicDetailList = topicMapper.getByTopicName(topicName);
        for (TopicDetail topicDetail : topicDetailList) {
            int topicId = topicDetail.getTopicId();
            topicDetail.setCollectNum(this.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(this.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(this.getCommentNum(topicId));
        }

        return topicDetailList;
    }

    public Collect collectedByUid(Integer topicId, Integer uid) {
        return topicMapper.collectedByUid(topicId, uid);
    }

    public Like likedByUid(Integer topicId, Integer uid) {
        return topicMapper.likedByUid(topicId, uid);
    }

    public List<TopicDetail> getLatestTopic() {
        List<TopicDetail> topicDetailList = topicMapper.getLatestTopic();
        for (TopicDetail topicDetail : topicDetailList) {
            int topicId = topicDetail.getTopicId();
            topicDetail.setCollectNum(this.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(this.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(this.getCommentNum(topicId));
        }
        return topicDetailList;
    }

    @Override
    public List<TopicDetail> getHotTopic() {
        List<TopicDetail> topicDetailList = topicMapper.getAllTopic();
        List<TopicDetail> resultList = new ArrayList<>();
        // 将topicId和总数(评论、点赞、收藏)，按键值对形式存储
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

        for (TopicDetail topicDetail : topicDetailList) {
            int topicId = topicDetail.getTopicId();
            int collectNum = this.getCollectNumByTopicId(topicId);
            int likeNum = this.getLikeNumByTopicId(topicId);
            int commentNum = this.getCommentNum(topicId);
            int total = collectNum + likeNum + commentNum;
            map.put(topicId, total);
        }
        // 将map.entrySet()转换成list{1=100, 2=150}
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        // 遍历集合
        for (Map.Entry<Integer, Integer> mapping : list){
            int topicId = mapping.getKey();
            TopicDetail topicDetail = topicMapper.getTopicById(topicId);
            topicDetail.setCollectNum(this.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(this.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(this.getCommentNum(topicId));
            resultList.add(topicDetail);
        }
        return resultList;
    }

}
