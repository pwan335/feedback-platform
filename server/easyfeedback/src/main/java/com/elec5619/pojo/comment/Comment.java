package com.elec5619.pojo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private Long topicId;
    private Long uid;
    private String username;
    private String content;
    private Timestamp createTime;
    private List<Reply> replyList = new ArrayList<>();
}
