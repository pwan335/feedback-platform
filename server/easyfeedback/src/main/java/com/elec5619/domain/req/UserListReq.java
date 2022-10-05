package com.elec5619.domain.req;

import lombok.Data;

@Data
public class UserListReq extends PageInfo {
    private String userName;
    private String email;
    private String tel;
    private String userType;
}
