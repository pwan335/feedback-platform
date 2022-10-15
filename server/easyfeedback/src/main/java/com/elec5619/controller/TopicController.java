package com.elec5619.controller;

import com.elec5619.controller.response.Code;
import com.elec5619.controller.response.Result;
import com.elec5619.domain.Page;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.TopicQuery;
import com.elec5619.domain.topic.Collect;
import com.elec5619.domain.topic.Like;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.UserData;
import com.elec5619.service.TopicService;
import com.elec5619.service.UserService;
import com.elec5619.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/topics")
@Slf4j
public class TopicController {

    @Value(("${web.topic-img-path}"))
    private String uploadPath;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    // TODO 还要对图片进行结果封装和处理
    // TODO 对时间格式进行处理
    @PostMapping("/name")
    public Result getByTopicName(@RequestBody TopicQuery topicQuery, HttpServletRequest request){
        PageInfo<TopicDetail> topicList = new PageInfo<TopicDetail>();
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                topicList = topicService.getByTopicName(topicQuery, user);
            }else{
                topicList = topicService.getByTopicName(topicQuery, null);
            }
        }else{
            topicList = topicService.getByTopicName(topicQuery, null);
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据话题名搜索成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/{topicId}")
    public Result getByTopicId(@PathVariable Long topicId, HttpServletRequest request){
        List<TopicDetail> topicList;
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                topicList = topicService.getByTopicId(topicId, user);
            }else{
                topicList = topicService.getByTopicId(topicId, null);
            }
        }else{
            topicList = topicService.getByTopicId(topicId, null);
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据话题id搜索成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/latest")
    public Result getLatestTopic(HttpServletRequest request, @RequestParam int pageNum, @RequestParam int pageSize){
        PageInfo<TopicDetail> topicList = new PageInfo<TopicDetail>();
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                topicList = topicService.getLatestTopic(user, page);
            }else{
                topicList = topicService.getLatestTopic(null, page);
            }
        }else{
            topicList = topicService.getLatestTopic(null, page);
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据时间获取最新话题成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/hot")
    public Result getHotTopic(HttpServletResponse response ,HttpServletRequest request, @RequestParam int pageNum, @RequestParam int pageSize){
        response.setHeader("Access-Control-Allow-Origin", "http:localhost:8080/");
        PageInfo<TopicDetail> topicList = new PageInfo<TopicDetail>();
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);

        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if("user".equals(user.getRole())){
                topicList = topicService.getHotTopic(user, page);
            }else{
                topicList = topicService.getHotTopic(null, page);
            }
        }else{
            topicList = topicService.getHotTopic(null, page);
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "根据时间获取热门话题成功" : "数据查询失败，请重试！";
        return new Result(code, topicList, msg);
    }

    @PostMapping("/collect")
    public Result saveCollect(@RequestBody Collect collect, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if(!"user".equals(user.getRole())){
                return new Result(Code.SAVE_ERR,false,"pm收藏出现异常！");
            }
            collect.setUid(userService.getUidByEmail(user.getEmail()));
            collect.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = topicService.saveCollect(collect);
            String msg = "用户收藏成功";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        return new Result(Code.SAVE_ERR,false,"用户未登陆，请登录后在尝试！");
    }

    @PostMapping("/like")
    public Result saveLike(@RequestBody Like like, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if(!"user".equals(user.getRole())){
                return new Result(Code.SAVE_ERR,false,"pm点赞出现异常！");
            }
            like.setUid(userService.getUidByEmail(user.getEmail()));
            like.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = topicService.saveLike(like);
            String msg = "用户点赞成功";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        return new Result(Code.SAVE_ERR,false,"用户未登陆，请登录后在尝试！");
    }

    @DeleteMapping("/collect")
    public Result deleteCollect(@RequestBody Collect collect, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if(!"user".equals(user.getRole())){
                return new Result(Code.DELETE_ERR,false,"pm取消收藏现异常！");
            }
            collect.setUid(userService.getUidByEmail(user.getEmail()));
            boolean flag = topicService.delectCollect(collect);
            String msg = "用户取消收藏成功";
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);
        }
        return new Result(Code.DELETE_ERR,false,"用户未登陆，请登录后在尝试！");
    }

    @DeleteMapping("/like")
    public Result DelectLike(@RequestBody Like like, HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if(!"user".equals(user.getRole())){
                return new Result(Code.DELETE_ERR,false,"pm取消点赞现异常！");
            }
            like.setUid(userService.getUidByEmail(user.getEmail()));
            boolean flag = topicService.delectLike(like);
            String msg = "用户取消点赞成功";
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);
        }
        return new Result(Code.DELETE_ERR,false,"用户未登陆，请登录后在尝试！");
    }

    @GetMapping("/user/data")
    public Result getUserData(HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            if(!"user".equals(user.getRole())){
                return new Result(Code.GET_ERR,false,"获取用户数据异常");
            }
            UserData userData = topicService.getUserData(userService.getUidByEmail(user.getEmail()), user.getRole());
            Integer code = userData != null ? Code.GET_OK : Code.GET_ERR;
            String msg = userData != null ? "获取用户数据成功" : "获取用户数据失败";
            return new Result(code, userData, msg);
        }
        return new Result(Code.GET_ERR,false,"用户未登陆，请登录后再尝试！");
    }

    @PostMapping("/image/save")
    public Result uploadImg(@RequestPart("file") MultipartFile[] files, @RequestPart("topicId") String topicId, HttpServletRequest request) throws IOException {
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        Long topicId1 = Long.parseLong(topicId);
        if (token == null) {
            return new Result(Code.SAVE_ERR, false, "用户未登陆，请登录后再尝试！");
        }
        SimpleUser user = TokenUtils.parseToken(token);
        List<String> visibleUriList = new ArrayList<>();
        List<String> saveUriList = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename(); //获取文件原名
            String visibleUri = "/" + fileName;
            String saveUri = uploadPath + "/" + fileName;

            log.info("图片原文件名={} 图片访问地址={} 图片保存真实地址={}", fileName, visibleUri, saveUri);
            File saveFile = new File(saveUri);

            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            try {
                file.transferTo(saveFile);
                visibleUriList.add(visibleUri);
                saveUriList.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(Code.SAVE_ERR, false, "用户上传头像失败");
            }
        }

        Integer flag = topicService.uploadImg(topicId1, files) != null? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag > 0 ? "用户上传头像成功" : "用户上传头像失败";
        return new Result(flag, visibleUriList, msg);
    }


//    @GetMapping("/topic/{topicId}/user/{uid}/collect")
//    public Result collectedByUid(@PathVariable Long topicId, @PathVariable Long uid){
//        Collect collect = topicService.collectedByUid(topicId, uid);
//        boolean collected = collect != null ? true : false;
//        Integer code = collect != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是收藏了，没有就是未收藏
//        String msg = collect != null ? "该用户已收藏该话题" : "该用户未收藏该话题";
//        return new Result(code, collected, msg);
//    }
//
////    @GetMapping("/topic_id/{topicId}/uid/{uid}/actions/like")
//    @GetMapping("/topic/{topicId}/user/{uid}/like")
//    public Result likedByUid(@PathVariable Long topicId, @PathVariable Long uid){
//        Like like = topicService.likedByUid(topicId, uid);
//        boolean collected = like != null ? true : false;
//        Integer code = like != null ? Code.GET_OK : Code.GET_OK; // 查询得到就是点赞了，没有就是未点赞
//        String msg = like != null ? "该用户已点赞该话题" : "该用户未点赞该话题";
//        return new Result(code, collected, msg);
//    }


}
