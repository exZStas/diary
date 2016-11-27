package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.MailDAO;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

import javax.mail.MessagingException;

public class MailBean {

    @Inject
    private MailDAO mailDAO;

    public void sendMailHTML(String from, String to, String subject, String content) throws ServiceException {
        ValidationUtils.ifNullOrTrimEmpty(from, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "from");
        ValidationUtils.ifNullOrTrimEmpty(to, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "to");
        ValidationUtils.ifNullOrTrimEmpty(subject, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "subject");
        ValidationUtils.ifNullOrTrimEmpty(content, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "content");

        try {
            mailDAO.sendMailHTML(from, to, subject, content);
        } catch (MessagingException e) {
            ExceptionFactory.throwServiceException(e, ErrorType.CANNOT_SEND_MAIL, to);
        }
    }
}
