package com.elec5619.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elec5619.annotation.ValidateToken;
import com.elec5619.domain.*;
import com.elec5619.domain.req.UserLoginReq;
import com.elec5619.domain.req.UserModifyReq;
import com.elec5619.domain.req.UserRegisterReq;
import com.elec5619.domain.resp.UserResp;
import com.elec5619.service.UserPhotoService;
import com.elec5619.service.UserService;
import com.elec5619.util.CheckCodeUtil;
import com.elec5619.util.ImgUtil;
import com.elec5619.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserPhotoService userPhotoService;
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        User user = userService.getById(id);
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? "" : "data query failure！ try again";
        return new Result(code, user, msg);
    }

    @GetMapping("/check/code/{email}")
    public Result getCheckCode(@PathVariable String  email){
        String code= CheckCodeUtil.getCode();
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("2402714619@qq.com");
        account.setUser("2402714619@qq.com");
        account.setPass("wgtythlxxoajdida");
        String msg=MailUtil.send(account,email,"register code",code,false);
        System.out.println(msg);
        Constant.map.put(email,new CheckCodeMsg(email,code));
        return Result.success(null);
    }

    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result register(@Validated UserRegisterReq req){
        if(!req.getPassword().equals(req.getConfirmPassword())){
            return Result.fail(ErrorCode.PWD_ERROR);
        }
        User user=new User();
        BeanUtil.copyProperties(req,user);

        List<User> dbUser=userService.lambdaQuery().eq(User::getEmail,req.getEmail()).or().eq(User::getPhoneNumber,req.getPhoneNumber()).list();
        if(!CollectionUtil.isEmpty(dbUser)){
            return Result.fail(ErrorCode.USER_EXISTS);
        }
        //验证码校验
        CheckCodeMsg checkCodeMsg=Constant.map.get(req.getEmail());
        if(checkCodeMsg==null){
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        if(System.currentTimeMillis()-checkCodeMsg.getExpireTime()>5*60*1000){
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        if(!req.getCode().equals(checkCodeMsg.getCode())){
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        String sign= UUID.randomUUID().toString().replace("-","");
        user.setSign(sign);
        //普通用户
        user.setUserType("2");
        userService.save(user);
        if(req.getPicFile()!=null){
            String fileName=req.getPicFile().getOriginalFilename();
            String last=fileName.substring(fileName.indexOf("."));
            if(StringUtils.isEmpty(last)||(!".jpg".equalsIgnoreCase(last)&&!".png".equalsIgnoreCase(last))){
                return Result.fail(ErrorCode.EMPTYPARMS);
            }
            String picUrl=ImgUtil.saveImg(req.getPicFile());
            UserPhoto userPhoto=new UserPhoto();
            userPhoto.setUid(user.getUserId());
            userPhoto.setPhoto(picUrl);
            userPhotoService.save(userPhoto);

        }
        return Result.success(null);
    }


    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginReq req){
        List<User> dbUser=userService.lambdaQuery().eq(User::getPassword,req.getPassword()).eq(User::getUserType,req.getUserType())
                .and(wrapper->wrapper.eq(User::getEmail,req.getKey()).or().eq(User::getPhoneNumber,req.getKey())).list();
        if(CollectionUtil.isEmpty(dbUser)){
            return Result.fail(ErrorCode.USER_EXISTS);
        }

        User loginUser=dbUser.get(0);
        if(loginUser==null){
            return Result.fail(ErrorCode.USER_NOT_FOUND);
        }
        HashMap<String,String> map=new HashMap<>();
        map.put("userName",loginUser.getUserName());
        map.put("userID",loginUser.getUserId()+"");
        map.put("userType",loginUser.getUserType());
        String token= JwtUtil.creatToken(map,loginUser.getSign());
        UserResp resp=new UserResp();
        BeanUtil.copyProperties(loginUser,resp);
        resp.setToken(token);
        QueryWrapper<UserPhoto> photoQueryWrapper=new QueryWrapper<>();
        UserPhoto userPhoto=userPhotoService.getOne(photoQueryWrapper.eq("uid",loginUser.getUserId()));
        if(userPhoto!=null){
            resp.setPic(userPhoto.getPhoto());
        }
        return Result.success(resp);

    }

    @PostMapping("/modify")
    @ValidateToken
    public Result modifyUser(UserModifyReq req){
        if(!req.getUserId().equals(req.getMoidfyId())){
            return Result.fail(ErrorCode.AUTH_ERROR);
        }
        QueryWrapper<User> userQueryWrapper=new
                QueryWrapper<>();
        User dbUser=userService.getOne(userQueryWrapper.eq("uid",req.getUserId()));
        if(dbUser==null){
            return Result.fail(ErrorCode.USER_NOT_FOUND);
        }
        Long userId=dbUser.getUserId();
        BeanUtil.copyProperties(req,dbUser);
        if(req.getPicFile()!=null){
            String fileName=req.getPicFile().getOriginalFilename();
            String last=fileName.substring(fileName.indexOf("."));
            if(StringUtils.isEmpty(last)||(!".jpg".equalsIgnoreCase(last)&&!".png".equalsIgnoreCase(last))){
                return Result.fail(ErrorCode.EMPTYPARMS);
            }
            String picUrl=ImgUtil.saveImg(req.getPicFile());
            QueryWrapper<UserPhoto> photoQueryWrapper=new QueryWrapper<>();
            UserPhoto userPhoto=userPhotoService.getOne(photoQueryWrapper.eq("uid",userId));
            if(userPhoto==null){
                userPhoto=new UserPhoto();
            }
            userPhoto.setUid(userId);
            userPhoto.setPhoto(picUrl);
            userPhotoService.saveOrUpdate(userPhoto);
        }
        dbUser.setUserId(userId);
        userService.updateById(dbUser);
        return Result.success(null);
    }
}
