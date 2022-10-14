package com.elec5619.domain.pm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductManager {
    private String role;
    private Long pmId;
    private String pmName;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private String avatar;
    private String company;
    private Timestamp createTime;
    private Timestamp updateTime;

}
