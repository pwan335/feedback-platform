package com.elec5619.controller;

import com.elec5619.controller.response.Code;
import com.elec5619.controller.response.Result;
import com.elec5619.domain.Page;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.comment.Comment;
import com.elec5619.domain.comment.Reply;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.user.User;
import com.elec5619.service.CommentService;
import com.elec5619.service.PmService;
import com.elec5619.service.UserService;
import com.elec5619.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PmService pmService;

    @GetMapping("/topicId")
    public Result listComment(@RequestParam Long topicId, @RequestParam int pageNum, @RequestParam int pageSize){
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        PageInfo<Comment> commentList = commentService.listComment(topicId, page);
        Integer code = commentList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = commentList != null ? "Search for comments based on topic id successfully" : "Data query failed, please try again!";
        return new Result(code, commentList, msg);
    }

    @PostMapping("/comment")
    public Result saveTopComment(@RequestBody Comment comment, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            System.out.println(user);
            if("user".equals(user.getRole())){
                comment.setRole("user");
                User userDetail = userService.getUserByEmail(user.getEmail());
                comment.setUid(userDetail.getUid());
            }else{
                comment.setRole("pm");
                ProductManager pmDetail = pmService.getPmByEmail(user.getEmail());
                comment.setUid(pmDetail.getPmId());
            }
            comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = commentService.saveTopComment(comment);
            String msg = "User comments successful";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        return new Result(Code.SAVE_ERR,false,"The user has not logged in, please log in and try again!");
    }

    @PostMapping("/reply")
    public Result saveTreeComment(@RequestBody Reply reply, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                User userDetail = userService.getUserByEmail(user.getEmail());
                reply.setFromUid(userDetail.getUid());
                reply.setFromUname(userDetail.getUserName());
            }else {
                ProductManager pmDetail = pmService.getPmByEmail(user.getEmail());
                reply.setFromUid(pmDetail.getPmId());
                reply.setFromUname(pmDetail.getPmName());
            }
            reply.setFromRole(user.getRole());
            reply.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = commentService.saveTreeComment(reply);
            String msg = "The user replied to the comment successfully";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        return new Result(Code.SAVE_ERR,false,"The user has not logged in, please log in and try again!");
    }

    // 删除评论、修改评论
    // 根据用户id和评论id去删除，分开comment和reply进行
    // 检查用户是否登陆
    @DeleteMapping("/top-comment/{id}")
    public Result deleteTopComment(HttpServletRequest request, @PathVariable Long id){
        boolean flag = false;
        String msg = "";
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                User userDetail = userService.getUserByEmail(user.getEmail());
                flag = commentService.delectTopComment(id, user.getRole(), userDetail.getUid());
                msg = flag ? "User deleted comment successfully" : "User failed to delete comment";
            }else{
                System.out.println("Add pm to delete any comment function");
            }
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);
        }
        return new Result(Code.DELETE_ERR,false,"The user has not logged in, please log in and try again!");
    }

    @DeleteMapping("/tree-comment/{id}")
    public Result deleteTreeComment(HttpServletRequest request, @PathVariable Long id){
        boolean flag = false;
        String msg = "";
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                User userDetail = userService.getUserByEmail(user.getEmail());
                flag = commentService.delectTreeComment(id, user.getRole(), userDetail.getUid());
                msg = flag ? "User deleted comment successfully" : "User failed to delete comment";
            }else{
                System.out.println("Add pm to delete any comment function");
            }
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);
        }
        return new Result(Code.DELETE_ERR,false,"The user has not logged in, please log in and try again!");
    }
}
