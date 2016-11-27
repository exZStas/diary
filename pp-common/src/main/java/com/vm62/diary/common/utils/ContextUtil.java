package com.vm62.diary.common.utils;

import com.vm62.diary.common.ServiceException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextUtil {

    private static final String FROM_MAIL_ADDRESS = "fromMailAddress";
    private static final String APPLICATION_URL = "applicationURL";
    private static final String USER_SESSION_TIMEOUT = "userSessionTimeout";
    private static final String SMTP_HOST = "smtpHost";
    private static final String EMAIL_PASSWORD = "emailPassword";

    public Integer getUserSessionTimeout() throws ServiceException {
        Object attribute = getContextAttribute(USER_SESSION_TIMEOUT);
        return attribute != null ? (Integer) attribute : null;
    }

    public String getSmtpHost() throws ServiceException {
        Object smtpHost = getContextAttribute(SMTP_HOST);
        return smtpHost != null ? (String) smtpHost : null;
    }

    public String getEmailPassword () throws ServiceException {
        Object password = getContextAttribute(EMAIL_PASSWORD);
        return password != null ? (String) password : null;
    }

    public String getFromMailAddress() throws ServiceException {
        Object fromMailAddress = getContextAttribute(FROM_MAIL_ADDRESS);
        return fromMailAddress != null ? (String) fromMailAddress : null;
    }

    public String getApplicationURL() throws ServiceException {
        Object applicationURL = getContextAttribute(APPLICATION_URL);
        return applicationURL != null ? (String) applicationURL : null;
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
