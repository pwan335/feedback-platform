package com.elec5619.service;

import com.elec5619.domain.Page;
import com.elec5619.domain.comment.Comment;
import com.elec5619.domain.comment.Reply;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest

public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testListComment(){
        Page page = new Page();
        page.setPageNum(1);
        page.setPageSize(2);
        PageInfo<Comment> commentList = commentService.listComment(3L, page);
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
