package com.elec5619.domain.resp;

import lombok.Data;

@Data
public class UserResp {
    private Long userId;
    private String userName;
    private String email;
    private String address;
    private String phoneNumber;
    private String hobby;
    private String pic;
    private String userType;
    private String token;

}
