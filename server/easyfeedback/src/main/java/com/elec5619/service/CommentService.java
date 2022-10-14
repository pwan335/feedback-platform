package com.elec5619.service;

import com.elec5619.domain.Page;
import com.elec5619.domain.comment.Comment;
import com.elec5619.domain.comment.Reply;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService {
    /**
     * 展示该话题所有评论
     * @param topicId
     * @return
     */
    PageInfo<Comment> listComment(Long topicId, Page page);

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


    /**
     * 删除一级评论
     * @param id
     * @param uid
     * @return
     */
    boolean delectTopComment(Long id, String role, Long uid);

    /**
     * 删除二级评论
     * @param id
     * @param uid
     * @return
     */
    boolean delectTreeComment(Long id, String role, Long uid);
}
