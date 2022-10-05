package com.elec5619.controller;

import com.elec5619.pojo.comment.Comment;
import com.elec5619.pojo.comment.Reply;
import com.elec5619.pojo.topic.TopicDetail;
import com.elec5619.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{topicId}")
    public Result listComment(@PathVariable Long topicId){
        List<Comment> commentList = commentService.listComment(topicId);
        Integer code = commentList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = commentList != null ? "The search for comments based on topic ID was successful" : "Data query failed, please try again!";
        return new Result(code, commentList, msg);
    }

    @PostMapping("/comment")
    public Result saveTopComment(@RequestBody Comment comment){
        boolean flag = commentService.saveTopComment(comment);
        return new Result(flag ? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    @PostMapping("/reply")
    public Result saveTreeComment(@RequestBody Reply reply){
        boolean flag = commentService.saveTreeComment(reply);
        return new Result(flag ? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

}
