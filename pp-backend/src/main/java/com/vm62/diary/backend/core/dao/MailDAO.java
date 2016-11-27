package com.vm62.diary.backend.core.dao;


import com.google.inject.Inject;
import com.sun.mail.smtp.SMTPTransport;
import com.vm62.diary.backend.core.bean.SessionBean;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ContextUtil;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
public class MailDAO {
    @Inject
    private ContextUtil contextUtil;
    @Inject
    private SessionBean sessionBean;

    public void sendMailHTML (String from, String to, String subject, String content) throws ServiceException, MessagingException {
        sendMail(from, to, subject, content, "text/html; charset=utf8");
    }

    private void sendMail(String from, String to, String subject, String content, String contentType) throws ServiceException, MessagingException {
        Session session = sessionBean.getMailSession();
        String host = contextUtil.getSmtpHost();
        String username = contextUtil.getFromMailAddress();
        String password = contextUtil.getEmailPassword();

        MimeMessage mimeMessage = new MimeMessage(session);
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);

        mimeMessage.addRecipient(Message.RecipientType.TO, toAddress);
        mimeMessage.setFrom(fromAddress);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(content, contentType);

        SMTPTransport transport = (SMTPTransport)session.getTransport("smtps");
        transport.connect(host, username, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();

    }
}
