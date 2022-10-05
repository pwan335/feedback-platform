package com.elec5619.domain.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Data
public class UserRegisterReq {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    private String address;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "电话不能为空")
    private String phoneNumber;
    private String hobby;
    private MultipartFile picFile;
    @NotBlank(message = "验证码不能为空")
    private String code;

}
