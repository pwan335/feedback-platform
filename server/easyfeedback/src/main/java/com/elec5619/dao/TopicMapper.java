package com.elec5619.dao;

import com.elec5619.pojo.Topic;
import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper {

    @Select("select * from tbl_topic where topic_id = #{topicId}")
    Topic getByTopicId(Integer topicId);

//    @Select("select * from tbl_topic where topicName like #{topicName}")
    List<TopicDetail> getByTopicName(String topicName);

    //查找数据：收藏、点赞、评论
    Integer getCollectNumByTopicId(Integer topicId);
    Integer getLikeNumByTopicId(Integer topicId);
    Integer getCommentNum(Integer topicId);


    //查询指定用户是否收藏该话题
    @Select("select * from tbl_collects WHERE topic_id = #{topicId} and uid = #{uid}")
    Collect collectedByUid(Integer topicId, Integer uid);
    //查询指定用户是否点赞该话题
    @Select("select * from tbl_likes WHERE topic_id = #{topicId} and uid = #{uid}")
    Like likedByUid(Integer topicId, Integer uid);

    @Select("select * from tbl_topic order by date desc")
    List<TopicDetail> getLatestTopic();

    @Select("select * from tbl_topic")
    List<TopicDetail> getAllTopic();

    @Select("select * from tbl_topic where topic_id = #{topicId}")
    TopicDetail getTopicById(Integer topicId);

    //用户收藏话题
    @Insert("insert into tbl_collects (topic_id, uid, date) values(#{topicId}, #{uid}, #{date})")
    int saveCollect(Collect collect);

    //用户点赞话题
    @Insert("insert into tbl_likes (topic_id, uid, date) values(#{topicId}, #{uid}, #{date})")
    int saveLike(Like like);


}
