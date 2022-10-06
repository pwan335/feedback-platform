package com.elec5619.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PostCollect {
    private Long collectId;
    private String topicId;
    private String userId;
    private Date crtTm;
}
