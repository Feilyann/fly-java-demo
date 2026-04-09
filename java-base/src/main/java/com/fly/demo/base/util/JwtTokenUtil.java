package com.fly.demo.base.util;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {

    public String createToken(String username) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("sub", "test");
        payloads.put("iss", "creatar-globe");
        Date now = new Date();
        payloads.put("iat", now.getTime());
        payloads.put("exp", now.getTime() + 1800000L);
        return JWT.create().withHeader(headers).withPayload(payloads).sign(Algorithm.HMAC256("test"));
    }

    public boolean validated(String token, String secretKey) {
        try {
            cn.hutool.jwt.JWT jwt = JWTUtil.parseToken(token);
            Object exp = jwt.getPayload("exp");
            if (ObjectUtil.isEmpty(exp)) {
                return false;
            }
            long expire = ((NumberWithFormat) exp).longValue();
            boolean flag = expire - System.currentTimeMillis() > 0;
            return jwt.verify(JWTSignerUtil.hs256(secretKey.getBytes())) && flag;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

}
