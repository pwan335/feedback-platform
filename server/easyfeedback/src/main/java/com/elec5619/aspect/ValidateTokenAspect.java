package com.elec5619.aspect;

import cn.hutool.core.util.ReflectUtil;
import com.elec5619.annotation.ValidateToken;
import com.elec5619.dao.UserDao;
import com.elec5619.domain.ErrorCode;
import com.elec5619.domain.User;
import com.elec5619.exception.ServiceException;
import com.elec5619.util.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateTokenAspect {
    @Autowired
    private UserDao userDao;


    @Before(value = "@annotation(validateToken)")
    public void validateToken(JoinPoint joinPoint, ValidateToken validateToken) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object token = ReflectUtil.getFieldValue(args[0], validateToken.token());
        String account = (String) ReflectUtil.getFieldValue(args[0], validateToken.userId());
        tokenValidation(account, (String) token);
    }

    private void tokenValidation(String userId,String token){
        User user=userDao.getByUid(userId);
        if(user==null){
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        try {
            JwtUtil.getToken(token,user.getSign());
        } catch (Exception e) {
            throw  new ServiceException(ErrorCode.TOKEN_ERROR);
        }
    }
}
