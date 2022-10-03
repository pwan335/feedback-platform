package com.elec5619.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long uid;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private Timestamp createTime;

}
