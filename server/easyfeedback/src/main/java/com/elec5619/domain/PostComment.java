package com.elec5619.domain;


import lombok.Data;

@Data
public class PostComment {
    private Long commentId;
    private String content;
    private String topicId;
    private String userId;
    private String crtTm;
}
