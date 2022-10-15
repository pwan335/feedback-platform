package com.elec5619.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.elec5619.domain.SimpleUser;

import java.util.Date;

public class TokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    //token到期时间10小时
    private static final long EXPIRE_TIME= 10*60*60*1000;
    //密钥盐
    private static final String TOKEN_SECRET="user_login_token";

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String sign(SimpleUser user){

        String token=null;
        try {
            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    .withClaim("role", user.getRole())
                    //存放数据
                    .withClaim("email",user.getEmail())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException| JWTCreationException je) {

        }
        return token;
    }


    /**
     * token验证
     * @param token
     * @return
     */
    public static Boolean verify(String token){
        token = token.replace(TokenUtils.TOKEN_PREFIX, "");
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("role: " + decodedJWT.getClaim("role").asString());
            System.out.println("userEmail: " + decodedJWT.getClaim("email").asString());
            System.out.println("过期时间：      " + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static SimpleUser parseToken(String token){
        SimpleUser user = new SimpleUser();

        token = token.replace(TokenUtils.TOKEN_PREFIX, "");
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        user.setRole(decodedJWT.getClaim("role").asString());
        user.setEmail(decodedJWT.getClaim("email").asString());
        return user;
    }

}
