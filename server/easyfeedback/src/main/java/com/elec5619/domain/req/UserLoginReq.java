package com.elec5619.domain.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginReq {
    @NotBlank(message = "请输入电话或者邮箱")
    private String key;
    @NotBlank
    private String password;
    @NotBlank
    private String userType;
}
