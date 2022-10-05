package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_pmPhoto")
public class PmPhoto {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("pm_id")
    private Long pmId;
    @TableField("photo")
    private String photo;
}
