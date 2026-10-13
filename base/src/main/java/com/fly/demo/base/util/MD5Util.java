package com.fly.demo.base.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String saltMd5Password(String password, String salt) {
        return DigestUtils.md5Hex(password + salt);
    }
}
