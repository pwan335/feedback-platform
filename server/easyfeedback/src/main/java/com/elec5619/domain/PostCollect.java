package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tbl_collects")
public class PostCollect {
    @TableId(value = "id",type = IdType.AUTO)
    private Long collectId;
    @TableField(value = "topic_id")
    private String topicId;
    @TableField(value = "uid")
    private String userId;
    @TableField(value = "date")
    private Date crtTm;
}
