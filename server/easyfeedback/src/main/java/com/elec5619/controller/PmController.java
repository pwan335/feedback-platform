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
            return new Result(Code.SAVE_ERR, flag, "The email has been registered");
        }
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "User registration is successful, please go to email verification" : "User registration failed, username or mailbox already exists";

        return new Result(code, flag, msg);
    }

    @GetMapping("/activate/{pmName}")
    public Result activateByEmail(@PathVariable String pmName){
        boolean flag = pmService.updateByName(pmName);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "User activation is successful, please go to login" : "User activation failed";
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
        String msg = flag ? "PM login succeeded" : "Incorrect user email address or password";
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
        String msg = flag ? "The new password has been obtained successfully. Please go to the email to obtain and verify it." : "Get failed, please try again!";
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
            return new Result(Code.GET_ERR,false,"The user has not logged in, please log in and try again!");
        }
        SimpleUser user = TokenUtils.parseToken(token);
        pmDto.setEmail(user.getEmail());
        boolean flag = pmService.changePwd(pmDto);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "User password modified successfully" : "Failed to modify, the old password is incorrect！";
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
            String msg = pmDetail != null ? "Pm personal information query successful": "Information query failed, please try again";
            Integer code = pmDetail != null ? Code.GET_OK : Code.GET_ERR;
            return new Result(code, pmDetail, msg);
        }
        return new Result(Code.GET_ERR,null,"Pm is not logged in, please log in and try again!");
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
            String msg = flag ? "Successful modification of pm personal information": "Data modification failed. Please try again";
            Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
            return new Result(code, flag, msg);
        }
        return new Result(Code.UPDATE_ERR,null,"Pm is not logged in, please log in and try again!");
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



//    @PostMapping("/topic")
//    public Result createTopic(HttpServletRequest request, @RequestPart("file") MultipartFile[] files, @RequestPart("topicName") String topicName, @RequestPart("content") String content) throws IOException{
//        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
//        boolean flag = true;
//        boolean hasImg = true;
//        if (token == null){
//            return new Result(Code.SAVE_ERR,false,"pm未登陆，请登录后再尝试！");
//        }
//
//        Topic topic = new Topic();
//        topic.setTopicName(topicName);
//        topic.setContent(content);
//
//        SimpleUser pm = TokenUtils.parseToken(token);
//        ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
//        topic.setPmId(pmDetail.getPmId());
//        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
//
//        Long topicId = pmService.saveTopic(topic);
//        if (topicId <= 0){
//            flag = false;
//        }
//        for (MultipartFile file : files) {
//            hasImg = file.isEmpty() ? false : true;
//        }
//        if(!hasImg){
//            String msg = flag ? "pm创建topic成功" : "pm创建topic失败，请重试";
//            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
//        }
//        List<String> visibleUriList = topicService.uploadImg(topicId, files);
//        Integer code = visibleUriList != null ? Code.SAVE_OK : Code.SAVE_ERR;
//        String msg = visibleUriList != null ? "pm创建topic成功" : "pm创建topic失败，请重试";
//        return new Result(code, visibleUriList, msg);
//    }

    @PostMapping("/img-topic")
    public Result createImgTopic(HttpServletRequest request, @RequestPart("file") MultipartFile[] files, @RequestPart("topicName") String topicName, @RequestPart("content") String content) throws IOException{
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        System.out.println("!23123");
        boolean flag = true;
        if (token == null){
            return new Result(Code.SAVE_ERR,false,"Pm is not logged in, please log in and try again!");
        }


        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setContent(content);

        SimpleUser pm = TokenUtils.parseToken(token);
        ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
        topic.setPmId(pmDetail.getPmId());
        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
        System.out.println(topic);
        Long topicId = pmService.saveTopic(topic);
        if (topicId <= 0){
            flag = false;
            String msg = flag ? "Pm successfully created topic" : "Pm failed to create topic. Please try again";
            return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag,msg);
        }
        List<String> visibleUriList = topicService.uploadImg(topicId, files);
        Integer code = visibleUriList != null ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = visibleUriList != null ? "Pm successfully created topic" : "Pm failed to create topic. Please try again";
        return new Result(code, visibleUriList, msg);
    }

    @PostMapping("/topic")
    public Result createNoImgTopic(HttpServletRequest request, @RequestBody Topic topic) throws IOException{
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token == null){
            return new Result(Code.SAVE_ERR,false,"Pm has not logged in, please log in and try again!");
        }

        SimpleUser pm = TokenUtils.parseToken(token);
        ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
        topic.setPmId(pmDetail.getPmId());
        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));

        boolean flag = pmService.saveTopic(topic) > 0;
        String msg = flag ? "Pm created topic successfully" : "Pm failed to create topic. Please try again";
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        return new Result(code, flag, msg);
    }


    @DeleteMapping("/topic/{topicId}")
    public Result deleteTopic(HttpServletRequest request, @PathVariable Long topicId){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            boolean flag = pmService.deleteTopic(topicId);
            String msg = flag ? "Pm deleted topic successfully" : "Pm failed to create topic. Please try again";
            return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag,msg);

        }
        return new Result(Code.DELETE_ERR,false,"Pm has not logged in, please log in and try again!");
    }

    @GetMapping("/topic/data")
    public Result getTopicDataByPm(HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser pm = TokenUtils.parseToken(token);
            ProductManager pmDetail = pmService.getPmByEmail(pm.getEmail());
            List<PmTopic> pmTopicList = pmService.getTopicDataByPm(pmDetail.getPmId());
            String msg = pmTopicList != null ? "Get pm topic data successfully": "Data query failed, please try again";
            Integer code = pmTopicList != null ? Code.GET_OK : Code.GET_ERR;
            return new Result(code, pmTopicList, msg);
        }
        return new Result(Code.GET_ERR,null,"Pm has not logged in, please log in and try again!");
    }

    @PostMapping("/image/save")
    public Result uploadAvatar(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token == null){
            return new Result(Code.SAVE_ERR,false,"Pm has not logged in, please log in and try again!");
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
            return new Result(Code.SAVE_OK,visibleUri,"uploaded avatar successfully");
        }catch (IOException e){
            e.printStackTrace();
            return new Result(Code.SAVE_ERR,false,"Failed to upload avatar");
        }
    }

}
