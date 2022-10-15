package com.elec5619.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUser {
    private String role;
    private String uid;
    private String email;
    private String password;
}
