package com.vm62.diary.backend.core.bean;

import com.google.inject.Singleton;

import javax.mail.Session;
import java.security.Security;
import java.util.Properties;

@Singleton
public class SessionBean {

    public SessionBean() {}

    private Session mailSession;
    private String host;
    private String username;
    private String password;

    public void initSession(String host, String username, String password){
        this.host = host;
        this.username = username;
        this.password = password;
        Properties prop = System.getProperties();
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtps.host", host);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtps.auth", "true");
        prop.put("mail.smtps.quitwait", "false");
        this.mailSession = Session.getDefaultInstance(prop, null);

    }

    public void initSession (Session session){
        this.mailSession = session;
    }

    public Session getMailSession() {
        return this.mailSession;
    }

    public String getHostName(){
        return this.host;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
