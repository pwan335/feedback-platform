package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tbl_user")
public class User {

    @TableId(value = "uid",type = IdType.AUTO)
    private Long userId;
    @TableField("username")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("user_type")
    private String userType;
    @TableField(value = "email",updateStrategy = FieldStrategy.IGNORED)
    private String email;
    @TableField(value = "address",updateStrategy = FieldStrategy.IGNORED)
    private String address;
    @TableField(value = "phoneNumber",updateStrategy = FieldStrategy.IGNORED)
    private String phoneNumber;
    @TableField(value = "hobby",updateStrategy = FieldStrategy.IGNORED)
    private String hobby;
    @TableField("sign")
    private String sign;
    @TableField("create_time")
    private Date crtTm;
    @TableField("update_time")
    private Date updTm;
    @TableField(exist = false)
    private String pic;




}
