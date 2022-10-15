package com.elec5619.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    private String userName;
    private String email;
    private String password;
    private String newPwd;
    private String avatar;
    private Timestamp createTime;
    private int status;
}
