package com.elec5619.domain;

public enum ErrorCode {

    PWD_ERROR(100001,"密码与确认密码不一致"),
    USER_NOT_FOUND(100002,"用户不存在或密码错误"),
    USER_EXISTS(100003,"用户已存在"),
    TOKEN_ERROR(100004,"token验证失败"),
    CODE_ERROR(100005,"验证码错误或者已失效"),
    AUTH_ERROR(100006,"权限不足"),
    FAILED(99,"系统繁忙，请稍后再试！"),
    EMPTYPARMS(98,"请求参数校验失败");
    private Integer errorCode;
    private String errorMsg;

    ErrorCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
