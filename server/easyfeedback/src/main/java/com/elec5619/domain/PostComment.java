package com.elec5619.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_comment")
public class PostComment {
    @TableId(value = "id",type = IdType.AUTO)
    private Long commentId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "topic_id")
    private String topicId;
    @TableField(value = "uid")
    private String userId;
    @TableField(value = "date")
    private String crtTm;
}
