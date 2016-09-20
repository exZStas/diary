package com.vm62.diary.common.utils;

import com.vm62.diary.common.ServiceException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextUtil {

    private static final String HOST_EMAIL = "smtpHost";
    private static final String USER_EMAIL = "smtpUser";
    private static final String PASSWORD_EMAIL = "smtpPassword";
    private static final String USER_SESSION_TIMEOUT = "userSessionTimeout";

    public Integer getUserSessionTimeout() throws ServiceException {
        Object attribute = getContextAttribute(USER_SESSION_TIMEOUT);
        return attribute != null ? (Integer) attribute : null;
    }

    public String getHost() throws ServiceException {
        Object attribute = getContextAttribute(HOST_EMAIL);
        return attribute != null ? (String) attribute : null;
    }

    public String getUserEmail() throws ServiceException {
        Object attribute = getContextAttribute(USER_EMAIL);
        return attribute != null ? (String) attribute : null;
    }

    public String getPassword() throws ServiceException {
        Object attribute = getContextAttribute(PASSWORD_EMAIL);
        return attribute != null ? (String) attribute : null;
    }


    private Object getContextAttribute(String key) throws ServiceException {
        Object object = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            object = envCtx.lookup(key);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
