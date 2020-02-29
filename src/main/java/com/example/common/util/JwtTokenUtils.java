package com.example.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtTokenUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    public static final String BEARER_TOKEN_TYPE = "Bearer";

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
}
