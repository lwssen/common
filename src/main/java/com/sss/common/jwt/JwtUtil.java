package com.sss.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.management.JMRuntimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sss
 * @date: 2019-09-06 15:41
 **/
public class JwtUtil {

    // 过期时间24小时
    // private static final long EXPRIE_TIME = 24*60*60*1000;

    public static String SECRET = "sss-abcd123456";

    /**
     * 传入username userId 生成token
     *
     * @param username
     * @param userId
     * @return java.lang.String
     **/
    public static String createToken(String username, String userId) {
        //签发时间
        Date istDate = new Date();

        //设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 1);
        Date expiresDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withExpiresAt(expiresDate)
                .withIssuedAt(istDate)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    /**
     * 验证token是否过期
     *
     * @param token
     * @return java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim>
     **/
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //throw new RuntimeException("token过期或不存在！！！");
            throw new JMRuntimeException("token过期或不存在！！！");

        }

        return jwt.getClaims();
    }

    /**
     * 根据token拿到用户名
     *
     * @param token
     * @return java.lang.String
     **/
    public static String getUserName(String token) {
        return verifyToken(token).get("username").asString();
    }


    /**
     * 根据token拿到用户ID
     *
     * @param token
     * @return java.lang.String
     **/
    public static String getUserId(String token)  {
        return verifyToken(token).get("userId").asString();
    }
}
