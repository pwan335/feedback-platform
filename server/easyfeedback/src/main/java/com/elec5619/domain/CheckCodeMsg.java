package com.elec5619.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CheckCodeMsg {
    private String key;
    private String code;
    private long expireTime;

    public CheckCodeMsg(String key, String code) {
        this.key = key;
        this.code = code;
        this.expireTime = new Date().getTime();
    }
}
