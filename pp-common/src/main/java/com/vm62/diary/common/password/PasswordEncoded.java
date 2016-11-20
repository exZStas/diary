package com.vm62.diary.common.password;

import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

public class PasswordEncoded extends Password{
    private String passwordEncoded;

    public PasswordEncoded(String passwordEncoded){
        this.passwordEncoded = passwordEncoded;
    }

    @Override
    public void validate() throws ServiceException {
        ValidationUtils.ifNullOrEmpty(passwordEncoded, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "passwordEncoded");
    }

    @Override
    public PasswordEncoded encode() {
        return this;
    }

    @Override
    public String getAsString() {
        return passwordEncoded;
    }
}
