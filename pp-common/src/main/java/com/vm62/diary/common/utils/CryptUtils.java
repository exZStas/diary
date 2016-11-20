package com.vm62.diary.common.utils;

import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;

import java.security.MessageDigest;

public class CryptUtils {

    public static String md5(String value) throws ServiceException {
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(value.getBytes("UTF-8"));

            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
        } catch (Exception ex){
            ExceptionFactory.throwServiceException(ex, ErrorType.CANNOT_CALCULATE_VALUE, "MD5");
        }
        return sb.toString();
    }
}
