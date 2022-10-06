package com.elec5619.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PostLike {
    private Long likeId;
    private String topicId;
    private String userId;
    private Date crtTm;
}
