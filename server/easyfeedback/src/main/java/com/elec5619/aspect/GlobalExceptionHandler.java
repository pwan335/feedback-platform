package com.elec5619.aspect;


import com.elec5619.controller.Result;
import com.elec5619.domain.ErrorCode;
import com.elec5619.exception.BusinessException;
import com.elec5619.exception.ServiceException;
import com.elec5619.exception.SystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.List;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(HttpServletRequest request,
                                      MethodArgumentNotValidException ex) {
        // 生成返回结果
        Result errorResult = new Result();
        try {
            //获取所有属性校验错误
            List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
            if (fieldErrorList.isEmpty()){
                errorResult.setCode(ErrorCode.FAILED.getErrorCode());
                errorResult.setMsg(ErrorCode.FAILED.getErrorMsg());
                return errorResult;
            }
            //拼装属性校验失败具体信息
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : fieldErrorList) {
                sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            errorResult.setCode(ErrorCode.EMPTYPARMS.getErrorCode());
            errorResult.setMsg(sb.toString());
        } catch (Exception e) {
            errorResult.setMsg(ErrorCode.FAILED.getErrorMsg());
            errorResult.setCode(ErrorCode.FAILED.getErrorCode());
        }
        return errorResult;
    }


    @ExceptionHandler(Exception.class)
    public Result defaultExceptionHandler(HttpServletRequest request,
                                                  Exception ex){
        // 生成返回结果
        Result errorResult = new Result();


        if (ex instanceof ServiceException){
            ServiceException serviceException = (ServiceException) ex;
            errorResult.setCode(serviceException.getError_code());
            errorResult.setMsg(serviceException.getError_msg());
            return errorResult;
        }else {
            errorResult.setCode(ErrorCode.FAILED.getErrorCode());
            errorResult.setMsg(ErrorCode.FAILED.getErrorMsg());
        }
        return errorResult;
    }
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        return new Result(ex.getCode(),null,ex.getMessage());
    }

}
