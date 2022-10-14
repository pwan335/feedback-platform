package com.elec5619.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String role;
    private Long uid;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private String avatar;
    private Timestamp createTime;
    private Timestamp updateTime;
}
