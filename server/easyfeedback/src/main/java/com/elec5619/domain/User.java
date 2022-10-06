package com.elec5619.domain;


import lombok.Data;
import java.util.Date;

@Data

public class User {


    private Long userId;
    private String userName;
    private String password;
    private String userType;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private String sign;
    private Date crtTm;
    private Date updTm;
    private String pic;
}
