package com.vm62.diary.common.password;


import com.vm62.diary.common.ServiceException;

import java.io.Serializable;

public abstract class Password implements Serializable{
    public Password(){}

    /**
     * validate password by specified parameters
     */
    public abstract void validate() throws ServiceException;

    public abstract String getAsString();

    public abstract PasswordEncoded encode() throws ServiceException;
}
