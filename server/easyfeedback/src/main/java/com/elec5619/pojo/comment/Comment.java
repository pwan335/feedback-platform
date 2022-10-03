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
    private Integer id;
    private Integer topicId;
    private Integer uid;
    private String username;
    private String content;
    private Timestamp date;
    private List<Reply> replyList = new ArrayList<>();
}
