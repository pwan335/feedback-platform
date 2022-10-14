package com.elec5619.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private Long id;
    private Long commentId;
    private String fromRole;
    private Long fromUid;
    private String fromUname;
    private String fromAvatar;
    private String toRole;
    private Long toUid;
    private String toUname;
    private String replyType;
    private Long replyId;
    private String content;
    private Timestamp createTime;
}
