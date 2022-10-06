package com.elec5619.domain.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@Data
public class UserModifyReq  extends  BaseReq{
    private String userName;
    @NotBlank
    private String email;
    private String address;
    @NotBlank
    private String phoneNumber;
    private String hobby;
    private MultipartFile picFile;
    @NotBlank
    private String password;
    @NotBlank
    private String moidfyId;
    private String company;

}
