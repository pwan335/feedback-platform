package com.elec5619.service;

import com.elec5619.pojo.comment.Comment;
import com.elec5619.pojo.comment.Reply;

import java.util.List;

public interface CommentService {
    /**
     * 展示该话题所有评论
     * @param topicId
     * @return
     */
    List<Comment> listComment(Long topicId);

    /**
     * 用户插入一级评论
     * @param comment
     * @return
     */
    boolean saveTopComment(Comment comment);

    /**
     * 用户插入二级评论
     * @param reply
     */
    boolean saveTreeComment(Reply reply);



}
