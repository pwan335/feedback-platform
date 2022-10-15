package com.elec5619.dao;

import com.elec5619.domain.comment.Comment;
import com.elec5619.domain.comment.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //列出所有一级评论
    @Select("select * from tbl_comment where topic_id = #{topicId}") //order by desc
    List<Comment> listTopComment(Long topicId);

    //根据一级评论列出所有二级评论
    @Select("select * from tbl_reply where comment_id = #{commentId}")
    List<Reply> listTreeComment(Long commentId);

    //插入一级评论
    @Insert("insert into tbl_comment (topic_id,role, uid, content, create_time) values(#{topicId}, #{role}, #{uid}, #{content}, #{createTime})")
    Long saveTopComment(Comment comment);

    //插入二级评论
    @Insert("insert into tbl_reply (comment_id,from_role, from_uid, to_role, to_uid, reply_type, reply_id, content, create_time) " +
            "values(#{commentId},#{fromRole}, #{fromUid},#{toRole}, #{toUid}, #{replyType}, #{replyId} ,#{content}, #{createTime})")
    Long saveTreeComment(Reply reply);

    //删除一级评论
    @Delete("delete from tbl_comment where id = #{id} and (uid = #{uid} and role = #{role}) ")
    Long delectTopComment(Long id, String role, Long uid);

    //删除二级评论
    @Delete("delete from tbl_reply where id = #{id} and (from_uid = #{uid} and from_role = #{role})")
    Long delectTreeComment(Long id, String role, Long uid);
}
