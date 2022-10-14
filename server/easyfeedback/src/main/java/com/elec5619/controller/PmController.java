package com.elec5619.controller;

import com.elec5619.controller.response.Code;
import com.elec5619.controller.response.Result;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.pm.AuthPmDto;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.topic.PmTopic;
import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.user.User;
import com.elec5619.service.PmService;
import com.elec5619.service.TopicService;
import com.elec5619.utils.SendCloudAPI;
import com.elec5619.utils.TokenUtils;
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
import java.util.HashMap;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/pm")
@Slf4j
public class PmController {

    @Value(("${web.avatar-path}"))
    private String uploadPath;

    @Value(("${web.default-avatar}"))
    private String defaultImg;

    @Autowired
    private PmService pmService;

    @Autowired
    private TopicService topicService;

    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody AuthPmDto pmDto){
        pmDto.setStatus(0);
        pmDto.setAvatar(defaultImg);
        pmDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = pmService.saveUser(pmDto);
        if(flag){
            try{
                SendCloudAPI.sendEmail(null, pmDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return new Result(Code.SAVE_ERR, flag, "该邮箱已被注册");
        }
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "用户注册成功，请前往邮箱验证" : "用户注册失败，用户名或邮箱已存在";

        return new Result(code, flag, msg);
    }

    @GetMapping("/activate/{userName}")
    public Result activateByEmail(@PathVariable String pmName){
        boolean flag = pmService.updateByName(pmName);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "用户激活成功，请前往登陆" : "用户激活失败";
        return new Result(code, flag, msg);
    }

    @PostMapping("/login")
    public Result userLogin(HttpServletResponse response, @RequestBody SimpleUser user){
//        SimpleUser user = new SimpleUser();
//        user.setRole(role);
//        user.setEmail(email);
//        user.setPassword(password);

        if(!pmService.getPmStatus(user.getEmail())){
            return new Result(Code.GET_ERR,null, "You need to activate your account by email");
        }

        boolean flag = pmService.validPmLogin(user);
        Integer code = flag ? Code.GET_OK : Code.GET_ERR;
        String msg = flag ? "PM登陆成功" : "用户邮箱地址或者密码错误";
        if (flag){
            String token = TokenUtils.sign(user);
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("token",token);
            response.setHeader(TokenUtils.TOKEN_HEADER, TokenUtils.TOKEN_PREFIX + token);
            return new Result(code, hs, msg);
        }
        return new Result(code,null, msg);
    }

    //这里还需改进
    @PostMapping("/password/forget")
    public Result forgetPwd(HttpServletRequest request, @RequestBody AuthPmDto pmDto) throws IOException {
        boolean flag = pmService.forgetPwd(pmDto);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "获取新密码成功，请前往邮箱获取并验证" : "获取失败，请重试！";
        return new Result(code, flag, msg);
    }

//    @PostMapping("/password/verify")
//    public Result verifyOldPwd(HttpServletRequest request, @RequestBody AuthPmDto pmDto){
//        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
//        if(token == null){
//            return new Result(Code.GET_ERR,false,"用户未登陆，请登录后再尝试！");
//        }
//        SimpleUser user = TokenUtils.parseToken(token);
//        pmDto.setEmail(user.getEmail());
//        boolean flag = pmService.verifyOldPwd(pmDto);
//        Integer code = flag ? Code.GET_OK : Code.GET_ERR;
//        String msg = flag ? "验证用户旧密码成功" : "验证失败，请重试！";
//        return new Result(code, flag, msg);
//    }

    @PutMapping("/password/change")
    public Result changePwd(HttpServletRequest request, @RequestBody AuthPmDto pmDto){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token == null){
            return new Result(Code.GET_ERR,false,"用户未登陆，请登录后再尝试！");
        }
        SimpleUser user = TokenUtils.parseToken(token);
        pmDto.setEmail(user.getEmail());
        boolean flag = pmService.changePwd(pmDto);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "修改用户密码成功" : "修改失败，旧密码不正确！";
        return new Result(code, flag, msg);
    }

    @GetMapping("/profile")
    public Result getProfile(HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser pm = TokenUtils.parseToken(token);
            ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
            pmDetail.setRole("pm");
            String avatar = "/" + pmDetail.getAvatar();
            pmDetail.setAvatar(avatar);
//            User userProfile = userService.getProfile(userDetail.getUid());
            String msg = pmDetail != null ? "查询pm个人信息成功": "信息查询失败，请重试";
            Integer code = pmDetail != null ? Code.GET_OK : Code.GET_ERR;
            return new Result(code, pmDetail, msg);
        }
        return new Result(Code.GET_ERR,null,"pm未登陆，请登录后再尝试！");
    }

    @PutMapping("/profile")
    public Result editProfile(HttpServletRequest request, @RequestBody ProductManager newPm){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser pm = TokenUtils.parseToken(token);
            ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
            newPm.setPmId(pmDetail.getPmId());
            newPm.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = pmService.updateProfile(newPm);
            String msg = flag ? "修改pm个人信息成功": "数据修改失败，请重试";
            Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
            return new Result(code, flag, msg);
        }
        return new Result(Code.UPDATE_ERR,null,"pm未登陆，请登录后再尝试！");
    }

//    @PostMapping("/topic")
//    public Result createTopic(HttpServletRequest request, @RequestBody Topic topic){
//        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
//        if (token != null){
//            SimpleUser pm = TokenUtils.parseToken(token);
//            ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
//            topic.setPmId(pmDetail.getPmId());
//            topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
//            boolean flag = pmService.saveTopic(topic);
//            String msg = flag ? "pm创建topic成功" : "pm创建topic失败，请重试";
//            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
//        }
//        return new Result(Code.SAVE_ERR,false,"pm未登陆，请登录后再尝试！");
//    }

    @PostMapping("/topic")
    public Result createTopic(HttpServletRequest request, @RequestPart("file") MultipartFile[] files, @RequestPart("topicName") String topicName, @RequestPart("content") String content) throws IOException{
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        boolean flag = true;
        boolean hasImg = true;
        if (token == null){
            return new Result(Code.SAVE_ERR,false,"pm未登陆，请登录后再尝试！");
        }

        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setContent(content);

        SimpleUser pm = TokenUtils.parseToken(token);
        ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
        topic.setPmId(pmDetail.getPmId());
        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));

        Long topicId = pmService.saveTopic(topic);
        if (topicId <= 0){
            flag = false;
        }
        for (MultipartFile file : files) {
            hasImg = file != null ? true : false;
        }
        if(!hasImg){
            String msg = flag ? "pm创建topic成功" : "pm创建topic失败，请重试";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        List<String> visibleUriList = topicService.uploadImg(topicId, files);
        Integer code = visibleUriList != null ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = visibleUriList != null ? "pm创建topic成功" : "pm创建topic失败，请重试";
        return new Result(code, visibleUriList, msg);
    }


    @DeleteMapping("/topic/{topicId}")
    public Result deleteTopic(HttpServletRequest request, @PathVariable Long topicId){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            boolean flag = pmService.deleteTopic(topicId);
            String msg = flag ? "pm删除topic成功" : "pm删除topic失败，请重试";
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);

        }
        return new Result(Code.DELETE_ERR,false,"pm未登陆，请登录后再尝试！");
    }

    @GetMapping("/topic/data")
    public Result getTopicDataByPm(HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser pm = TokenUtils.parseToken(token);
            ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
            List<PmTopic> pmTopicList = pmService.getTopicDataByPm(pmDetail.getPmId());
            String msg = pmTopicList != null ? "获取pm话题数据成功": "数据查询失败，请重试";
            Integer code = pmTopicList != null ? Code.GET_OK : Code.GET_ERR;
            return new Result(code, pmTopicList, msg);
        }
        return new Result(Code.GET_ERR,null,"pm未登陆，请登录后再尝试！");
    }

    @PostMapping("/image/save")
    public Result uploadAvatar(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token == null){
            return new Result(Code.SAVE_ERR,false,"pm未登陆，请登录后再尝试！");
        }
        SimpleUser user = TokenUtils.parseToken(token);
        String fileName = file.getOriginalFilename(); //获取文件原名
        String visibleUri = "/" + fileName;
        String saveUri = uploadPath + "/" + fileName;

        log.info("图片原文件名={} 图片访问地址={} 图片保存真实地址={}",fileName,visibleUri,saveUri);
        File saveFile = new File(saveUri);

        if(!saveFile.exists()){
            saveFile.mkdir();
        }
        try {
            file.transferTo(saveFile);
            pmService.saveAvatar(user, fileName);
            return new Result(Code.SAVE_OK,visibleUri,"用户上传头像成功");
        }catch (IOException e){
            e.printStackTrace();
            return new Result(Code.SAVE_ERR,false,"用户上传头像失败");
        }
    }

}
