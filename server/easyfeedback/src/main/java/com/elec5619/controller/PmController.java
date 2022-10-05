package com.elec5619.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elec5619.annotation.ValidateToken;
import com.elec5619.domain.ErrorCode;
import com.elec5619.domain.User;
import com.elec5619.domain.UserPhoto;
import com.elec5619.domain.req.UserListReq;
import com.elec5619.domain.req.UserModifyReq;
import com.elec5619.service.UserPhotoService;
import com.elec5619.service.UserService;
import com.elec5619.util.ImgUtil;
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
    private UserPhotoService userPhotoService;
    @PostMapping("user/list")
    @ValidateToken
    public Result getUserList(@RequestBody @Validated  UserListReq req){
        Page page=new Page(req.getPageNum(),req.getPageSize());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(req.getUserName())){
            queryWrapper.eq("username",req.getUserName());
        }
        if(!StringUtils.isEmpty(req.getEmail())){
            queryWrapper.eq("email",req.getEmail());
        }
        if(!StringUtils.isEmpty(req.getTel())){
            queryWrapper.eq("tel",req.getTel());
        }
        if(!StringUtils.isEmpty(req.getUserType())){
            queryWrapper.eq("user_type",req.getUserType());
        }

        IPage<User> users=userService.page(page,queryWrapper);
        return Result.success(users);
    }
    @PostMapping("/modify")
    @ValidateToken
    public Result modifyUser(UserModifyReq req){
        QueryWrapper<User> userQueryWrapper=new
                QueryWrapper<>();
        User dbUser=userService.getOne(userQueryWrapper.eq("uid",req.getMoidfyId()));
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
            String picUrl= ImgUtil.saveImg(req.getPicFile());
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
