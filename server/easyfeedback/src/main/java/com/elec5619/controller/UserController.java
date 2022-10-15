package com.elec5619.controller;

import com.elec5619.controller.response.Code;
import com.elec5619.controller.response.Result;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.User;
import com.elec5619.service.UserService;
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

@CrossOrigin
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Value(("${web.avatar-path}"))
    private String uploadPath;

    @Value(("${web.default-avatar}"))
    private String defaultImg;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        User user = userService.getById(id);
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? "Get user success" : "Data query failed, please try again!";
        return new Result(code, user, msg);
    }

    @GetMapping("/profile")
    public Result getProfile(HttpServletRequest request){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            User userDetail = userService.getUserByEmail(user.getEmail());
            userDetail.setRole("user");
            String avatar = "/" + userDetail.getAvatar();
            userDetail.setAvatar(avatar);
//            User userProfile = userService.getProfile(userDetail.getUid());
            String msg = userDetail != null ? "Query the user's personal information successfully": "Information query failed, please try again";
            Integer code = userDetail != null ? Code.GET_OK : Code.GET_ERR;
            return new Result(code, userDetail, msg);
        }
        return new Result(Code.GET_ERR,null,"The user has not logged in, please log in and try again!");
    }

    @PutMapping("/profile")
    public Result editProfile(HttpServletRequest request, @RequestBody User newUser){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            User userDetail = userService.getUserByEmail(user.getEmail());
            newUser.setUid(userDetail.getUid());
            newUser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            boolean flag = userService.updateProfile(newUser);
            String msg = flag ? "Successful modification of user's personal information": "Failed to modify the information, please try again";
            Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
            return new Result(code, flag, msg);
        }
        return new Result(Code.UPDATE_ERR,null,"The user has not logged in, please log in and try again!");
    }

    @PostMapping("/login")
    public Result userLogin(HttpServletResponse response, @RequestBody SimpleUser user){
//        SimpleUser user = new SimpleUser();
//        user.setRole(role);
//        user.setEmail(email);
//        user.setPassword(password);

        if(!userService.getUserStatus(user.getEmail())){
            return new Result(Code.GET_ERR,null, "You need to activate your account by email");
        }

        boolean flag = userService.validUserLogin(user);
        Integer code = flag ? Code.GET_OK : Code.GET_ERR;
        String msg = flag ? "User logged in successfully" : "Incorrect user email address or password";
        if (flag){
            String token = TokenUtils.sign(user);
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("token",token);
            response.setHeader(TokenUtils.TOKEN_HEADER, TokenUtils.TOKEN_PREFIX + token);
            return new Result(code, hs, msg);
        }
        return new Result(code,null, msg);
    }

//    @PostMapping("/password/verify")
//    public Result verifyOldPwd(HttpServletRequest request, @RequestBody AuthUserDto userDto){
//        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
//        if(token == null){
//            return new Result(Code.GET_ERR,false,"用户未登陆，请登录后再尝试！");
//        }
//        SimpleUser user = TokenUtils.parseToken(token);
//        userDto.setEmail(user.getEmail());
//        boolean flag = userService.verifyOldPwd(userDto);
//        Integer code = flag ? Code.GET_OK : Code.GET_ERR;
//        String msg = flag ? "验证用户旧密码成功" : "验证失败，请重试！";
//        return new Result(code, flag, msg);
//    }

    @PutMapping("/password/change")
    public Result changePwd(HttpServletRequest request, @RequestBody AuthUserDto userDto){
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token == null){
            return new Result(Code.GET_ERR,false,"The user has not logged in, please log in and try again!");
        }
        SimpleUser user = TokenUtils.parseToken(token);
        userDto.setEmail(user.getEmail());
        boolean flag = userService.changePwd(userDto);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "User password modified successfully" : "Failed to modify, the old password is incorrect!";
        return new Result(code, flag, msg);
    }

    //这里还需改进
    @PostMapping("/password/forget")
    public Result forgetPwd(HttpServletRequest request, @RequestBody AuthUserDto userDto) throws IOException {
        boolean flag = userService.forgetPwd(userDto);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "The new password has been obtained successfully. Please go to the mailbox to obtain and verify it." : "Failed to get, please try again!";
        return new Result(code, flag, msg);
    }


    @GetMapping("/data/collect")
    public Result getTopicByCollected(HttpServletRequest request){
        List<TopicDetail> topicList = new ArrayList<>();
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            topicList = userService.getTopicByCollected(user);
        }else{
            return new Result(Code.GET_ERR,false,"The user has not logged in, please log in and try again!");
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "Get the user's favorite topic successfully" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/data/like")
    public Result getTopicByLiked(HttpServletRequest request){
        List<TopicDetail> topicList = new ArrayList<>();
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            topicList = userService.getTopicByLiked(user);
        }else{
            return new Result(Code.GET_ERR,false,"The user has not logged in, please log in and try again!");
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "Successful topic of getting likes from users" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @GetMapping("/data/comment")
    public Result getTopicByComment(HttpServletRequest request){
        List<TopicDetail> topicList = new ArrayList<>();
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token != null){
            SimpleUser user = TokenUtils.parseToken(token);
            topicList = userService.getTopicByComment(user);
        }else{
            return new Result(Code.GET_ERR,false,"The user has not logged in, please log in and try again!");
        }
        Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = topicList != null ? "Get the topic of user comments successfully" : "Data query failed, please try again!";
        return new Result(code, topicList, msg);
    }

    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody AuthUserDto userDto) throws IOException {
        userDto.setStatus(0);
        userDto.setAvatar(defaultImg);
        userDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = userService.saveUser(userDto);
        if(flag){
            SendCloudAPI.sendEmail(userDto, null);
        }else{
            return new Result(Code.SAVE_ERR, flag, "This email has been registered");
        }
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "The user has registered successfully. Please go to the mailbox to verify." : "User registration failed, username or mailbox already exists";

        return new Result(code, flag, msg);
    }

    @GetMapping("/activate/{userName}")
    public Result activateByEmail(@PathVariable String userName){
        boolean flag = userService.updateByName(userName);
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "User activation is successful, please go to login" : "User activation failed";
        return new Result(code, flag, msg);
    }

    @PostMapping("/image/save")
    public Result uploadAvatar(@RequestPart("file")MultipartFile file, HttpServletRequest request) throws IOException {
        String token = request.getHeader(TokenUtils.TOKEN_HEADER);
        if(token == null){
            return new Result(Code.SAVE_ERR,false,"The user has not logged in, please log in and try again!");
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
            userService.saveAvatar(user, fileName);
            return new Result(Code.SAVE_OK,visibleUri,"Upload avatar successfully");
        }catch (IOException e){
            e.printStackTrace();
            return new Result(Code.SAVE_ERR,false,"Failed to upload avatar");
        }
    }


}
