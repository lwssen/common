package com.sss.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sss.common.entity.TokenInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 **/
public class MyJwtUtil {
    // 过期时间1小时
     private static final long EXPRIE_TIME = 60*60*1000;

    public static String SECRET = "SSS-abcd123456";

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
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.DATE, 1);
//        Date expiresDate = nowTime.getTime();
        Date expiresDate = new Date(System.currentTimeMillis()+EXPRIE_TIME);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)
                .withClaim("userName", username)
                .withClaim("userId", userId)
                .withExpiresAt(expiresDate)
                .withIssuedAt(istDate)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }



    public static String createRefreshToken(String username, String userId) {
        //签发时间
        Date istDate = new Date();

        //设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 1);
        Date expiresDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String refreshToken = JWT.create()
                .withHeader(map)
                .withClaim("userName", username)
                .withClaim("userId", userId)
                .withExpiresAt(expiresDate)
                .withIssuedAt(istDate)
                .sign(Algorithm.HMAC256(SECRET));
         return refreshToken;
    }

    /**
     *  构建存在token信息对象
     * @param username
     * @param userId
     * @return com.hciot.mes.saas.entity.TokenInfo
     * @author sss
    **/
    public static TokenInfo createTokenInfo(String username, String userId){
        String token = createToken(username, userId);
        String refreshToken = createRefreshToken(username, userId);
        TokenInfo tokenInfo = new TokenInfo(token, refreshToken, EXPRIE_TIME/1000);
        return  tokenInfo;
    }

    /**
     * 验证token是否过期
     *
     * @param token
     * @return java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim>
     **/
    public static Map<String, Object> verifyToken(String token) throws TokenExpiredException {
        Map<String, Object> resultMap = new HashMap();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt=null;
        jwt = verifier.verify(token);
//        try {
//          jwt = verifier.verify(token);
//        }catch (Exception ex){
//            throw new RuntimeException("token过期或不存在");
//        }
        Map<String, Claim> claims = jwt.getClaims();
        for (Map.Entry<String, Claim> claimEntry : claims.entrySet()) {
            resultMap.put((String)claimEntry .getKey(), ((Claim)claimEntry .getValue()).asString());
        }
        if (jwt.getExpiresAt() != null) {
            resultMap.put("expTime", Long.valueOf(jwt.getExpiresAt().getTime()));
        }
        return resultMap;
    }

    /**
     * 根据token拿到用户名
     *
     * @param token
     * @return java.lang.String
     **/
    public static String getUserName(String token)  {
        return (String) verifyToken(token).get("userName");
    }


    /**
     * 根据token拿到用户ID
     *
     * @param token
     * @return java.lang.String
     **/
    public static String getUserId(String token) {
        return (String) verifyToken(token).get("userId");
    }




}
