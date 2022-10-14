package com.elec5619.domain.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmTopic {
    private Long topicId;
    private String topicName;
    private Timestamp createTime;
    private int collectNum;
    private int commentNum;
    private int likeNum;
}
