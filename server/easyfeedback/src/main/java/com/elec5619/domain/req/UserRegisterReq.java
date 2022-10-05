package com.elec5619.domain.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Data
public class UserRegisterReq {
    @NotBlank(message = "username can't be null")
    private String userName;
    @NotBlank(message = "password can't be null")
    private String password;
    @NotBlank(message = "password confirmation cannot be empty")
    private String confirmPassword;
    private String address;
    @NotBlank(message = "email can't be empty")
    private String email;
    @NotBlank(message = "phone number can't be empty")
    private String phoneNumber;
    private String hobby;
    private MultipartFile picFile;
    @NotBlank(message = "verification code can't be empty")
    private String code;

}
