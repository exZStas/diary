package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;

import java.util.List;

@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class MailNotificationBean {

    @Inject
    private UserDAO userDAO;

    // TODO: 20.11.2016 implement mail sending
//    public List<Long> sendRegistrationNotification() {
//        List<User> users = userDAO.getUsersByRegistrationStatus(false);
//
//        for(User user : users){
//
//        }
//    }
}
