package com.elec5619.controller;

import com.elec5619.pojo.topic.Collect;
import com.elec5619.pojo.topic.Like;
import com.elec5619.pojo.topic.TopicDetail;
import com.elec5619.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // TODO 还要对图片进行结果封装和处理
    // TODO 对时间格式进行处理
    @GetMapping("/name/{topicName}")
    public Result getByTopicName(@PathVariable String topicName){
        List<TopicDetail> topicList = topicService.getByTopicName(topicName);
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据话题名搜索成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/topic/{topicId}/user/{uid}/collect")
    public Result collectedByUid(@PathVariable Integer topicId, @PathVariable Integer uid){
        Collect collect = topicService.collectedByUid(topicId, uid);
        boolean collected = collect != null ? true : false;
        Integer code = collect != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是收藏了，没有就是未收藏
        String msg = collect != null ? "该用户已收藏该话题" : "该用户未收藏该话题";
        return new Result(code, collected, msg);
    }

//    @GetMapping("/topic_id/{topicId}/uid/{uid}/actions/like")
    @GetMapping("/topic/{topicId}/user/{uid}/like")
    public Result likedByUid(@PathVariable Integer topicId, @PathVariable Integer uid){
        Like like = topicService.likedByUid(topicId, uid);
        boolean collected = like != null ? true : false;
        Integer code = like != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是点赞了，没有就是未点赞
        String msg = like != null ? "该用户已点赞该话题" : "该用户未点赞该话题";
        return new Result(code, collected, msg);
    }

    @GetMapping("/latest")
    public Result getLatestTopic(){
        List<TopicDetail> topicList = topicService.getLatestTopic();
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据时间获取最新话题成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/hot")
    public Result getHotTopic(){
        List<TopicDetail> topicList = topicService.getHotTopic();
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据时间获取最新话题成功" : "数据查询失败，请重试！";
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
