package com.elec5619.service;

import com.elec5619.pojo.comment.Comment;
import com.elec5619.pojo.comment.Reply;
import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import org.apache.ibatis.annotations.Insert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest

public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testListComment(){
        List<Comment> commentList = commentService.listComment(2L);
        System.out.println(commentList);
    }


    @Test
    public void testSaveTopComment(){
        Comment comment = new Comment();
        comment.setUid(1L);
        comment.setTopicId(2L);
        comment.setContent("comment test 123");
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = commentService.saveTopComment(comment);
        System.out.println(flag);
    }

    @Test
    public void testSaveTreeComment(){
        Reply reply = new Reply();
        reply.setCommentId(3L);
        reply.setFromUid(1L);
        reply.setToUid(1L);
        reply.setReplyType("comment");
        reply.setReplyId(3L);
        reply.setContent("reply test 123");
        reply.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = commentService.saveTreeComment(reply);
        System.out.println(flag);
    }
}
