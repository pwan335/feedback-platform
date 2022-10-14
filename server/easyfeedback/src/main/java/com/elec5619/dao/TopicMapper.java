package com.elec5619.dao;

import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.topic.Collect;
import com.elec5619.domain.topic.Like;
import com.elec5619.domain.topic.TopicDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopicMapper {

    @Select("select * from tbl_topic where topic_id = #{topicId}")
    List<TopicDetail> getByTopicId(Long topicId);

//    @Select("select * from tbl_topic where topicName like #{topicName}")
    List<TopicDetail> getByTopicName(String topicName);

    //查找数据：收藏、点赞、评论
    Integer getCollectNumByTopicId(Long topicId);
    Integer getLikeNumByTopicId(Long topicId);
    Integer getCommentNum(Long topicId);


    //查询指定用户是否收藏该话题
    @Select("select * from tbl_collects WHERE topic_id = #{topicId} and uid = #{uid}")
    Collect collectedByUid(Long topicId, Long uid);
    //查询指定用户是否点赞该话题
    @Select("select * from tbl_likes WHERE topic_id = #{topicId} and uid = #{uid}")
    Like likedByUid(Long topicId, Long uid);

    @Select("select * from tbl_topic order by create_time desc")
    List<TopicDetail> getLatestTopic();

    @Select("select * from tbl_topic")
    List<TopicDetail> getAllTopic();

    @Select("select * from tbl_topic where topic_id = #{topicId}")
    TopicDetail getTopicById(Long topicId);

    @Select("select photo from tbl_topicPhoto where topic_id = #{topicId}")
    List<String> getTopicImgById(Long topicId);

    //用户收藏话题
    @Insert("insert into tbl_collects (topic_id, uid, create_time) values(#{topicId}, #{uid}, #{createTime})")
    Long saveCollect(Collect collect);

    //用户点赞话题
    @Insert("insert into tbl_likes (topic_id, uid, create_time) values(#{topicId}, #{uid}, #{createTime})")
    Long saveLike(Like like);

    @Delete("delete from tbl_collects where topic_id = #{topicId} and uid = #{uid}")
    Long delectCollect(Collect collect);

    @Delete("delete from tbl_likes where topic_id = #{topicId} and uid = #{uid}")
    Long delectLike(Like like);

    @Select("select count(*) from tbl_collects where uid = #{uid}")
    Integer getCollectNumByUser(Long uid);

    @Select("select count(*) from tbl_likes where uid = #{uid}")
    Integer getLikeNumByUser(Long uid);

    @Select("select count(*) from tbl_comment where uid = #{uid} and role = #{role}")
    Integer getCommentNumByUser(Long uid, String role);

    @Select("select count(*) from tbl_reply where from_uid = #{uid} and from_role = #{role}")
    Integer getReplyNumByUser(Long uid, String role);

    @Insert("insert into tbl_topicPhoto (topic_id, photo) values(#{topicId}, #{uri})")
    int saveImg(Long topicId, String uri);
}
