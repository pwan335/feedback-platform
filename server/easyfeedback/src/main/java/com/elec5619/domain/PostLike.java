package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tbl_likes")
public class PostLike {
    @TableId(value = "id",type = IdType.AUTO)
    private Long likeId;
    @TableField(value = "topic_id")
    private String topicId;
    @TableField(value = "uid")
    private String userId;
    @TableField(value = "date")
    private Date crtTm;
}
