package com.elec5619.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("topic")
public class Topic {
    @TableId(value = "topic_id",type = IdType.AUTO)
    private Long topicId;
    @TableField("topic_name")
    private String title;
    @TableField("content")
    private String content;
    @TableField("pm_id")
    private String userId;
    @TableField("create_time")
    private Date createTime;
    private String pic;

}
