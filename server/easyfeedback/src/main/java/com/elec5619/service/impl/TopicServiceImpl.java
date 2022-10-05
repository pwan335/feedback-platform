package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.TopicDao;
import com.elec5619.dao.TopicMapper;
import com.elec5619.domain.Topic;
import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import com.elec5619.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicDao, Topic> implements TopicService{

    @Autowired
    private TopicMapper topicMapper;

//    public Integer getCollectNumByTopicId(Integer topicId){
//        return topicMapper.getCollectNumByTopicId(topicId) != null ? topicMapper.getCollectNumByTopicId(topicId) : 0;
//    }
//
//    public Integer getLikeNumByTopicId(Integer topicId){
//        return topicMapper.getLikeNumByTopicId(topicId) != null ? topicMapper.getLikeNumByTopicId(topicId) : 0;
//    }
//
//    public Integer getCommentNum(Integer topicId){
//        return topicMapper.getCommentNum(topicId) != null ? topicMapper.getCommentNum(topicId) : 0;
//    }

    public List<TopicDetail> setTopicDetail(List<TopicDetail> topicDetailList){
        for (TopicDetail topicDetail : topicDetailList) {
            Long topicId = topicDetail.getTopicId();
            topicDetail.setCollectNum(topicMapper.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(topicMapper.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(topicMapper.getCommentNum(topicId));
        }
        return topicDetailList;
    }

    public List<TopicDetail> getByTopicName(String topicName){
        topicName = "%" + topicName + "%";
        List<TopicDetail> topicDetailList = topicMapper.getByTopicName(topicName);
        topicDetailList = this.setTopicDetail(topicDetailList);
        return topicDetailList;
    }

    public Collect collectedByUid(Long topicId, Long uid) {
        return topicMapper.collectedByUid(topicId, uid);
    }

    public Like likedByUid(Long topicId, Long uid) {
        return topicMapper.likedByUid(topicId, uid);
    }

    public List<TopicDetail> getLatestTopic() {
        List<TopicDetail> topicDetailList = topicMapper.getLatestTopic();
        topicDetailList = this.setTopicDetail(topicDetailList);
        return topicDetailList;
    }

    @Override
    public List<TopicDetail> getHotTopic() {
        List<TopicDetail> topicDetailList = topicMapper.getAllTopic();
        List<TopicDetail> resultList = new ArrayList<>();
        // 将topicId和总数(评论、点赞、收藏)，按键值对形式存储
        Map<Long, Integer> map = new TreeMap<Long, Integer>();

        for (TopicDetail topicDetail : topicDetailList) {
            Long topicId = topicDetail.getTopicId();
            int collectNum = topicMapper.getCollectNumByTopicId(topicId);
            int likeNum = topicMapper.getLikeNumByTopicId(topicId);
            int commentNum = topicMapper.getCommentNum(topicId);
            int total = collectNum + likeNum + commentNum;
            map.put(topicId, total);
        }
        // 将map.entrySet()转换成list{1=100, 2=150}
        List<Map.Entry<Long, Integer>> list = new ArrayList<>(map.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
            @Override
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        // 遍历集合
        for (Map.Entry<Long, Integer> mapping : list){
            Long topicId = mapping.getKey();
            TopicDetail topicDetail = topicMapper.getTopicById(topicId);
            topicDetail.setCollectNum(topicMapper.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(topicMapper.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(topicMapper.getCommentNum(topicId));
            resultList.add(topicDetail);
        }
        return resultList;
    }

    @Override
    public boolean saveLike(Like like) {
        return topicMapper.saveLike(like) > 0;
    }

    @Override
    public boolean saveCollect(Collect collect) {
        return topicMapper.saveCollect(collect) > 0;
    }

}
