package com.elec5619.domain.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCommentReq extends BaseReq{
    @NotBlank
    private String content;
    @NotBlank
    private String topicId;
}
