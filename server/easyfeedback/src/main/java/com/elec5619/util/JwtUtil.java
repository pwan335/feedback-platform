package com.elec5619.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtil {

    public static String creatToken(Map<String,String> map, String sing){
        JWTCreator.Builder builder= JWT.create();
        map.forEach((k,v)->{builder.withClaim(k,v);});
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,7);

        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(sing));
    }

    public static DecodedJWT getToken(String token,String sing){
        return JWT.require(Algorithm.HMAC256(sing)).build().verify(token);
    }
}
