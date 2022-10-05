package com.elec5619.controller;

import com.elec5619.domain.ErrorCode;

public class Result {

    private Integer code;
    private Object data;
    private String msg;

    public Result() {
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(ErrorCode errorCode,Object data){
        this.code = errorCode.getErrorCode();
        this.data = data;
        this.msg = errorCode.getErrorMsg();
    }
    public static  Result success(Object data){
        Result result=new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(data);
        return  result;
    }

    public static Result fail(ErrorCode errorCode){
        Result result=new Result();
        result.setMsg(errorCode.getErrorMsg());
        result.setCode(errorCode.getErrorCode());
        return  result;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
