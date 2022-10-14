package com.elec5619.domain.topic;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Collect {

    private Long id;
    private Long topicId;
    private Long uid;
    private Timestamp createTime;

}
