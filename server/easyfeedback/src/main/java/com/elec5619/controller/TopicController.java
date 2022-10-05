package com.elec5619.controller;

import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import com.elec5619.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elec5619.annotation.ValidateToken;
import com.elec5619.domain.*;
import com.elec5619.domain.req.PostCollectReq;
import com.elec5619.domain.req.PostCommentReq;
import com.elec5619.domain.req.PostLikeReq;
import com.elec5619.domain.req.PostTopicReq;
import com.elec5619.service.*;
import com.elec5619.util.ImgUtil;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private PostLikeService likeService;
    @Autowired
    private PostCollectService postCollectService;
    @Autowired
    private TopicPhotoService topicPhotoService;
    @Autowired
    private UserService userService;
    /*评论*/
    @PostMapping("post/comment")
    @ValidateToken
    public Result postComment(@RequestBody PostCommentReq req){
        PostComment postComment=new PostComment();
        BeanUtil.copyProperties(req,postComment);
        postCommentService.save(postComment);
        return Result.success(postComment);
    }
    /*发表帖子*/
    @PostMapping("post/topic")
    @ValidateToken
    public Result postTopic(PostTopicReq req){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        User user=userService.getOne(queryWrapper.eq("uid",req.getUserId()));
        if("2".equals(user.getUserType())){
            return Result.fail(ErrorCode.AUTH_ERROR);
        }
        Topic topic=new Topic();
        BeanUtil.copyProperties(req,topic);
        topic.setCreateTime(new Date());
        topicService.save(topic);
        if(req.getTopicPic()!=null){
            String fileName=req.getTopicPic().getOriginalFilename();
            String last=fileName.substring(fileName.indexOf("."));
            if(StringUtils.isEmpty(last)||(!".jpg".equalsIgnoreCase(last)&&!".png".equalsIgnoreCase(last))){
                return Result.fail(ErrorCode.EMPTYPARMS);
            }
            String picUrl= ImgUtil.saveImg(req.getTopicPic());
            TopicPhoto topicPhoto=new TopicPhoto();
            topicPhoto.setTopicId(topic.getTopicId());
            topicPhoto.setPhoto(picUrl);
            topicPhotoService.save(topicPhoto);
            topic.setPic(picUrl);
        }
        return Result.success(topic);
    }
    /*喜欢*/
    @PostMapping("post/like")
    @ValidateToken
    public Result postLike(@RequestBody PostLikeReq req){
        PostLike like=new PostLike();
        BeanUtil.copyProperties(req,like);
        like.setCrtTm(new Date());
        likeService.save(like);
        return Result.success(null);
    }
    /*收藏*/
    @PostMapping("post/collect")
    @ValidateToken
    public Result postCollect(@RequestBody PostCollectReq req){
        PostCollect collect=new PostCollect();
        BeanUtil.copyProperties(req,collect);
        collect.setCrtTm(new Date());
        postCollectService.save(collect);
        return Result.success(null);
    }

    // TODO 还要对图片进行结果封装和处理
    // TODO 对时间格式进行处理
    @GetMapping("/name/{topicName}")
    public Result getByTopicName(@PathVariable String topicName){
        List<TopicDetail> topicList = topicService.getByTopicName(topicName);
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "The search succeeds based on the topic name" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/topic/{topicId}/user/{uid}/collect")
    public Result collectedByUid(@PathVariable Long topicId, @PathVariable Long uid){
        Collect collect = topicService.collectedByUid(topicId, uid);
        boolean collected = collect != null ? true : false;
        Integer code = collect != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是收藏了，没有就是未收藏
        String msg = collect != null ? "The user has bookmarked the topic" : "This user has not bookmarked this topic";
        return new Result(code, collected, msg);
    }

//    @GetMapping("/topic_id/{topicId}/uid/{uid}/actions/like")
    @GetMapping("/topic/{topicId}/user/{uid}/like")
    public Result likedByUid(@PathVariable Long topicId, @PathVariable Long uid){
        Like like = topicService.likedByUid(topicId, uid);
        boolean collected = like != null ? true : false;
        Integer code = like != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是点赞了，没有就是未点赞
        String msg = like != null ? "The user has thumbed up on the topic" : "The user didn't thumb up on the topic";
        return new Result(code, collected, msg);
    }

    @GetMapping("/latest")
    public Result getLatestTopic(){
        List<TopicDetail> topicList = topicService.getLatestTopic();
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "Get the latest topic success according to time" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/hot")
    public Result getHotTopic(){
        List<TopicDetail> topicList = topicService.getHotTopic();
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "Get the latest topic success according to time" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @PostMapping("/collect")
    public Result saveCollect(@RequestBody Collect collect){
        boolean flag = topicService.saveCollect(collect);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    @PostMapping("/like")
    public Result saveLike(@RequestBody Like like){
        boolean flag = topicService.saveLike(like);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }


}
