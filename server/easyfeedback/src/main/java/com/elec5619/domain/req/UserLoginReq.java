package com.elec5619.domain.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginReq {
    @NotBlank(message = "please type in phone number or email")
    private String key;
    @NotBlank
    private String password;
    @NotBlank
    private String userType;
}
