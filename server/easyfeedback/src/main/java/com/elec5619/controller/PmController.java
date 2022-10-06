package com.elec5619.controller;


import cn.hutool.core.bean.BeanUtil;
import com.elec5619.annotation.ValidateToken;
import com.elec5619.domain.*;
import com.elec5619.domain.req.UserListReq;
import com.elec5619.domain.req.UserModifyReq;
import com.elec5619.service.PhotoService;
import com.elec5619.service.UserService;
import com.elec5619.util.ImgUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pm")
public class PmController {

    @Autowired
    private UserService userService;
    @Autowired
    private PhotoService photoService;
    @PostMapping("user/list")
    @ValidateToken
    public Result getUserList(@RequestBody @Validated  UserListReq req){
        Page<User> users=PageHelper.startPage(req.getPageNum(),req.getPageSize()).doSelectPage(()->userService.getUsers(req));
        return Result.success(users);
    }
    @PostMapping("/modify")
    @ValidateToken
    public Result modifyUser(UserModifyReq req){
        PmUser dbUser=userService.getPmUser(req.getMoidfyId());
        if(dbUser==null){
            return Result.fail(ErrorCode.USER_NOT_FOUND);
        }
        Long pmId=dbUser.getPmId();
        BeanUtil.copyProperties(req,dbUser);
        userService.updatePmUser(dbUser);
        User user=new User();
        BeanUtil.copyProperties(dbUser,user);
        user.setUserName(dbUser.getPmName());
        user.setUserId(Long.parseLong(dbUser.getUserId()));
        userService.updateUser(user);
        if(req.getPicFile()!=null){
            String fileName=req.getPicFile().getOriginalFilename();
            String last=fileName.substring(fileName.indexOf("."));
            if(StringUtils.isEmpty(last)||(!".jpg".equalsIgnoreCase(last)&&!".png".equalsIgnoreCase(last))){
                return Result.fail(ErrorCode.EMPTYPARMS);
            }
            String picUrl= ImgUtil.saveImg(req.getPicFile());
            PmPhoto pmPhoto= photoService.getPmPic(pmId+"");
            if(pmPhoto==null){
                pmPhoto=new PmPhoto();
            }
            pmPhoto.setPmId(pmId);
            pmPhoto.setPhoto(picUrl);
            photoService.saveOrUpdatePmPhoto(pmPhoto);
            UserPhoto userPhoto=photoService.getUserPic(user.getUserId()+"");
            if(userPhoto==null){
                userPhoto=new UserPhoto();
            }
            userPhoto.setUid(user.getUserId());
            userPhoto.setPhoto(picUrl);
            photoService.saveOrUpdateUserPhoto(userPhoto);
        }

        return Result.success(null);
    }
    @PostMapping("setPm")
    @ValidateToken
    public Result setUserToPm(@RequestBody SetPmReq req){
        User user=userService.getUserById(req.getModifyId());
        if(user==null||"1".equals(user.getUserType())){
            return Result.fail(ErrorCode.EMPTYPARMS);
        }
        PmUser pmUser=new PmUser();
        BeanUtil.copyProperties(user,pmUser);
        pmUser.setPmName(user.getUserName());
        userService.savePmUser(pmUser);
        user.setUserType("1");
        userService.updateUser(user);
        UserPhoto userPhoto=photoService.getUserPic(user.getUserId()+"");
        if(userPhoto!=null){
            PmPhoto pmPhoto=new PmPhoto();
            pmPhoto.setPhoto(userPhoto.getPhoto());
            pmPhoto.setPmId(pmPhoto.getPmId());
            photoService.savePmPhoto(pmPhoto);
        }
        return Result.success(null);
    }
}
