package com.elec5619.domain;


import lombok.Data;

import java.util.Date;

@Data
public class Topic {
    private Long topicId;
    private String title;
    private String content;
    private String userId;
    private Date createTime;
    private String pic;

}
