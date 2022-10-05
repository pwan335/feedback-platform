package com.elec5619.domain.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostLikeReq extends BaseReq{
    @NotBlank
    private String topicId;
}
