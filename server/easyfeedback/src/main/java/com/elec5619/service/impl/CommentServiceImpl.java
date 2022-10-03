package com.elec5619.service.impl;

import com.elec5619.dao.CommentMapper;
import com.elec5619.pojo.User;
import com.elec5619.pojo.comment.Comment;
import com.elec5619.pojo.comment.Reply;
import com.elec5619.service.CommentService;
import com.elec5619.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    public String getUserName(Long uid){
        User user = userService.getById(uid);
        return user.getUsername();
    }

    @Override
    public List<Comment> listComment(Long topicId) {
        List<Comment> commentList = commentMapper.listTopComment(topicId);
        for (Comment comment : commentList) {
            comment.setUsername(this.getUserName(comment.getUid()));
            List<Reply> replyList = commentMapper.listTreeComment(comment.getId());
            for (Reply reply : replyList) {
                reply.setFromUname(this.getUserName(reply.getFromUid()));
                reply.setToUname(this.getUserName(reply.getToUid()));
            }
            comment.setReplyList(replyList);
        }
        return commentList;
    }

    @Override
    public boolean saveTopComment(Comment comment) {
        return commentMapper.saveTopComment(comment) > 0;
    }

    @Override
    public boolean saveTreeComment(Reply reply) {
        return commentMapper.saveTreeComment(reply) > 0;
    }
}
