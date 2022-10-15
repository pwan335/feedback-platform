package com.elec5619.service.impl;

import com.elec5619.dao.CommentMapper;
import com.elec5619.dao.PmMapper;
import com.elec5619.dao.UserMapper;
import com.elec5619.domain.Page;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.user.User;
import com.elec5619.domain.comment.Comment;
import com.elec5619.domain.comment.Reply;
import com.elec5619.service.CommentService;
import com.elec5619.service.PmService;
import com.elec5619.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PmMapper pmMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PmService pmService;

    public String getUserName(Long uid){
        User user = userService.getById(uid);
        return user.getUserName();
    }

    public String getPmName(Long pmId){
        ProductManager pm = pmService.getById(pmId);
        return pm.getPmName();
    }


    @Override
    public PageInfo<Comment> listComment(Long topicId, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        List<Comment> commentList = commentMapper.listTopComment(topicId);
        for (Comment comment : commentList) {
            if("user".equals(comment.getRole())){
                comment.setUserName(this.getUserName(comment.getUid()));
                comment.setUserAvatar("/" + userMapper.getAvatar(comment.getUid()));
            }else{
                comment.setUserName(this.getPmName(comment.getUid()));
                comment.setUserAvatar("/" + pmMapper.getAvatar(comment.getUid()));
            }
            List<Reply> replyList = commentMapper.listTreeComment(comment.getId());
            for (Reply reply : replyList) {
                if("user".equals(reply.getFromRole())){
                    reply.setFromUname(this.getUserName(reply.getFromUid()));
                    reply.setFromAvatar("/" + userMapper.getAvatar(reply.getFromUid()));
                }else{
                    reply.setFromUname(this.getPmName(reply.getFromUid()));
                    reply.setFromAvatar("/" + pmMapper.getAvatar(reply.getFromUid()));
                }
                if("user".equals(reply.getToRole())){
                    reply.setToUname(this.getUserName(reply.getToUid()));
                }else{
                    reply.setToUname(this.getPmName(reply.getToUid()));
                }
//                reply.setFromUname(this.getUserName(reply.getFromUid()));
//                reply.setToUname(this.getUserName(reply.getToUid()));
            }
            comment.setReplyList(replyList);
        }
        return new PageInfo<>(commentList);
    }

    @Override
    public boolean saveTopComment(Comment comment) {
        return commentMapper.saveTopComment(comment) > 0;
    }

    @Override
    public boolean saveTreeComment(Reply reply) {
        return commentMapper.saveTreeComment(reply) > 0;
    }

    @Override
    public boolean delectTopComment(Long id, String role, Long uid) {
        return commentMapper.delectTopComment(id, role, uid) > 0;
    }

    @Override
    public boolean delectTreeComment(Long id, String role, Long uid) {
        return commentMapper.delectTreeComment(id, role, uid) > 0;
    }
}
