package com.elec5619.domain.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDetail {
    private Long topicId;
    private String topicName;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long pmId;
    private String pmName;
    private String pmAvatar;
    private Integer collectNum;
    private boolean collectState;
    private Integer commentNum;
    private Integer likeNum;
    private boolean likeState;
    private List<String> images;
}
