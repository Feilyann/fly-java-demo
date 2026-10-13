package com.fly.demo.base.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

@Slf4j
public class HMacUtil {

    public static void hmacTest() throws NoSuchAlgorithmException, InvalidKeyException {
        String provider = HMacUtil.getProvider("HmacSHA256");
        log.info("算法提供者: {}", provider);
        String secretKey = HMacUtil.createKey();
        log.info("my secret key is: {}", secretKey);
        long nextLong = new Random().nextLong();
        log.info("a random number: {}", nextLong);
    }

    public static String hmac(String secretKey, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        sha256_HMAC.init(secretKeySpec);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
    }

    public static String createKey() throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(KeyGenerator.getInstance("HmacSHA256").generateKey().getEncoded());
    }

    public static String getProvider(String algorithmName) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(algorithmName).getProvider().getName();
    }
}
