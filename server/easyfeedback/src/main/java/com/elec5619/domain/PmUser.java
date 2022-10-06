package com.elec5619.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PmUser {

    private Long pmId;
    private String pmName;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private String company;
    private Date date;
    private String userId;
}
