package com.elec5619.pojo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private Integer id;
    private Integer commentId;
    private Integer fromUid;
    private String fromUname;
    private Integer toUid;
    private String toUname;
    private String replyType;
    private Integer replyId;
    private String content;
    private Timestamp date;
}
