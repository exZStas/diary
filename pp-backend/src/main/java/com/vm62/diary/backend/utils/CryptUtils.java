package com.vm62.diary.backend.utils;


import com.vm62.diary.common.ServiceException;
import org.apache.commons.lang.RandomStringUtils;

import java.security.MessageDigest;

public class CryptUtils {

    public static String md5(String value) throws ServiceException {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(value.getBytes("UTF-8"));

            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String generateToken() {
        return RandomStringUtils.randomAlphanumeric(64);
    }
}