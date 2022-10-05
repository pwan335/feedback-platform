package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
@TableName("tbl_pm")
@Data
public class PmUser {

    @TableId(value = "pm_id",type = IdType.AUTO)
    private Long userId;
    @TableField("pm_name")
    private String userName;
    @TableField("password")
    private String password;
    @TableField(value = "email",updateStrategy = FieldStrategy.IGNORED)
    private String email;
    @TableField(value = "address",updateStrategy = FieldStrategy.IGNORED)
    private String address;
    @TableField(value = "phone_number",updateStrategy = FieldStrategy.IGNORED)
    private String phoneNumber;
    @TableField(value = "hobby",updateStrategy = FieldStrategy.IGNORED)
    private String hobby;
    @TableField("company")
    private String userType;
    @TableField("sign")
    private String sign;
    @TableField("create_time")
    private Date crtTm;
    @TableField("update_time")
    private Date updTm;
}
