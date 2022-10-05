package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_userPhoto")
public class UserPhoto {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("uid")
    private Long uid;
    @TableField("photo")
    private String photo;
}
