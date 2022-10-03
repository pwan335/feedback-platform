package com.elec5619.pojo.topic;

import lombok.Data;

import java.util.Date;

@Data
public class Like {
    private Integer id;
    private Integer topicId;
    private Integer uid;
    private Date date;
}
