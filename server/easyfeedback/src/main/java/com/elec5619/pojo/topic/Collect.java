package com.elec5619.pojo.topic;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;


@Data
public class Collect {

    private Long id;
    private Long topicId;
    private Long uid;
    private Timestamp createTime;

}
