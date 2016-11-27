package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.MailSubjects;
import com.vm62.diary.common.utils.ContextUtil;

import java.util.List;

import static com.vm62.diary.common.constants.GetParameters.REGISTRATION_ID;

@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class MailNotificationBean {

    @Inject
    private UserDAO userDAO;
    @Inject
    private MailBean mailBean;
    @Inject
    private ContextUtil contextUtil;
    @Inject
    private UserBean userBean;

    public void sendRegistrationNotification() throws ServiceException {
        List<User> users = userDAO.getUsersByRegistrationStatus(false);

        for(User user : users){
            if(!user.getMailSent()) {
                String from = contextUtil.getFromMailAddress();
                String content = prepareContent(user);
                mailBean.sendMailHTML(from, user.getEmail(), MailSubjects.REGISTRATION.getSubject(), content);

                user.setMailSent(Boolean.TRUE);
                userBean.updateUser(user);
            }
        }
    }

    private String prepareContent(User user) throws ServiceException {
        String rawContent = MailSubjects.REGISTRATION.getContent();
        String applicationURL = contextUtil.getApplicationURL();
        String content = rawContent + "</br></br>" + applicationURL + "/verifyRegistration?" + REGISTRATION_ID + "=" + user.getRegistrationId();
        return content;
    }
}
