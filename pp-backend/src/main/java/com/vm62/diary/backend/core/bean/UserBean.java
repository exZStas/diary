package com.vm62.diary.backend.core.bean;


import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;

import java.util.List;

import static com.vm62.diary.common.utils.ValidationUtils.ifNull;
import static com.vm62.diary.common.utils.ValidationUtils.ifNullOrEmpty;


@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class UserBean {

    @Inject
    private UserDAO userDAO;

    public User createUser(User user) throws ServiceException {
        ifNull(user, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "user");

        return userDAO.createUser(user);
    }

    public User getUserByEmail(String email) throws ServiceException {
        ifNullOrEmpty(email, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "email");

        return userDAO.getUserByEmail(email);
    }


    public User getUserById(Long userId)  {
        return userDAO.getUserById(userId);
    }

    public User setUserRegistrationStatus(Boolean registrationStatus, User user) throws ServiceException {
        ifNull(user, ErrorType.CANNOT_BE_NULL, "user");
        ifNull(registrationStatus, ErrorType.CANNOT_BE_NULL, "registrationStatus");

        return userDAO.setUserRegistrationStatus(registrationStatus, user);
    }

    public User updateUser(User user) throws ServiceException {
        ifNull(user, ErrorType.CANNOT_BE_NULL, "user");

        return userDAO.updateUser(user);
    }

    public User getUserByRegistrationId(String registrationId) throws ServiceException {
        ifNullOrEmpty(registrationId, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "registrationId");

        return userDAO.getUserByRegistrationId(registrationId);
    }

    public List<User> getAllUsers(){
        return userDAO.getUsers();
    }


}
