package com.elec5619.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    private Long topicId;
    private Long pmId;
    private String topicName;
    private String content;
    private Timestamp createTime;

}
