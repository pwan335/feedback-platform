package com.elec5619.dao;

import com.elec5619.pojo.comment.Comment;
import com.elec5619.pojo.comment.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    //列出所有一级评论
    @Select("select * from tbl_comment where topic_id = #{topicId}")
    List<Comment> listTopComment(Integer topicId);

    //根据一级评论列出所有二级评论
    @Select("select * from tbl_reply where comment_id = #{commentId}")
    List<Reply> listTreeComment(Integer commentId);

    //插入一级评论
    @Insert("insert into tbl_comment (topic_id, uid, content, date) values(#{topicId}, #{uid}, #{content}, #{date})")
    int saveTopComment(Comment comment);

    //插入二级评论
    @Insert("insert into tbl_reply (comment_id, from_uid, to_uid, reply_type, reply_id, content, date) " +
            "values(#{commentId}, #{fromUid}, #{toUid}, #{replyType}, #{replyId} ,#{content}, #{date})")
    int saveTreeComment(Reply reply);
}
