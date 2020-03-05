package com.example.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtTokenUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    /**
     * token 默认签发者
     */
    private static final String DEFAULT_ISSUER = "practice-exam";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    /**
     * refreshToken 30天过期
     */
    private static final int DEFAULT_EXPIRATION_AFTER_DAYS = 30;
    /**
     * token 2小时过期
     */
    private static final int DEFAULT_EXPIRATION_AFTER_HOURS = 8;

    /**
     * 获取token 的用户id
     *
     * @param secretKey 签发密钥
     * @param token     token 字符串
     * @return 用户id
     */
    @SuppressWarnings("Duplicates")
    public static Long getUserId(String secretKey, String token) {
        if(token.length()<BEARER_TOKEN_TYPE.length() + 1){
            return null;
        }
        try{
            String jwtToken = token.substring(BEARER_TOKEN_TYPE.length() + 1);
            Claims claims = Jwts.parser().setAllowedClockSkewSeconds(10).setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            //subject 就是userId, token 验证通过后，登录
            String userId = claims.getSubject();
            return Long.valueOf(userId);
        }catch (RuntimeException runtimeException){
            LOGGER.debug("token 解析错误");
            return null;
        }
    }

    public static String getRefreshUuid(String secretKey, String refreshToken) {
        try {
            Claims claims = Jwts.parser().setAllowedClockSkewSeconds(10).setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
            //subject 就是uuid
            return claims.getSubject();
        } catch (RuntimeException runtimeException) {
            LOGGER.debug("token 解析错误");
            return null;
        }
    }

    /**
     * @param secretKey 加密key
     * @param userId    用户的id
     * @return jwt token
     */
    public static String genToken(String secretKey, Long userId) {
        //制造jwt
        return BEARER_TOKEN_TYPE + " " + Jwts.builder().setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setIssuer(DEFAULT_ISSUER)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setNotBefore(new Date())
                .setExpiration(DateTime.now().plusHours(DEFAULT_EXPIRATION_AFTER_HOURS).toDate())
                .compact();
    }

    /**
     * @param secretKey  加密key
     * @param randomUuid random uuid
     * @return jwt refreshToken
     */
    public static String genRefreshToken(String secretKey, String randomUuid) {
        //制造jwt
        return Jwts.builder().setSubject(randomUuid)
                .setIssuedAt(new Date())
                .setIssuer(DEFAULT_ISSUER)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setNotBefore(new Date())
                .setExpiration(DateTime.now().plusDays(DEFAULT_EXPIRATION_AFTER_DAYS).toDate())
                .compact();
    }
}
