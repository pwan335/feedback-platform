package com.elec5619.domain.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PostTopicReq extends BaseReq{
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private MultipartFile topicPic;
}
