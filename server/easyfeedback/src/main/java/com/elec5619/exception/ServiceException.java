package com.elec5619.exception;

import com.elec5619.domain.ErrorCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 6604441723471366806L;

    private int error_code;
    private String error_msg;


    public ServiceException(String s) {
        super();
    }


    public ServiceException(int error_code, String error_msg) {
        super(error_msg);
        this.error_code = error_code;
        this.error_msg = error_msg;

    }
    public ServiceException(ErrorCode errorCode) {
        this.error_code = errorCode.getErrorCode();
        this.error_msg = errorCode.getErrorMsg();

    }

    public ServiceException(int error_code, String error_msg, Throwable throwable) {
        super(error_msg, throwable);
        this.error_code = error_code;
        this.error_msg = error_msg;

    }
}
