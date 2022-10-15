package com.elec5619.domain.pm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthPmDto {
    private String pmName;
    private String email;
    private String password;
    private String newPwd;
    private String company;
    private String avatar;
    private Timestamp createTime;
    private int status;
}
