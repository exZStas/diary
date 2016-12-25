package com.vm62.diary.backend.core;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.Password;
import com.vm62.diary.common.password.PasswordEncoded;
import com.vm62.diary.common.session.UserSessionHelper;
import com.vm62.diary.common.utils.ValidationUtils;

import java.util.Date;
import java.util.UUID;

public class LoginModule {

    private static final String MEN = "men";

    @Inject
    private UserBean userBean;

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserSessionHelper userSessionHelper;

    public User authorizeUser(String email, Password password) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(email, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "email");
        ValidationUtils.ifNullOrEmpty(password.getAsString(), ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "password");
        User user = userBean.getUserByEmail(email);
        if (user.getPassword().getAsString().equals(password.encode().getAsString())) {
            userSessionHelper.createUserSession(user.getId());
            return user;
        } else {
            ExceptionFactory.throwServiceException(ErrorType.CANNOT_LOGIN);
        }
        return null;
    }


    public User createUser(String firstName, String lastName, Password password, String sex, String studyGroup, Date birthday, String email) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(firstName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "first name");
        ValidationUtils.ifNullOrEmpty(lastName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "last name");
        ValidationUtils.ifNullOrEmpty(studyGroup, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "study group");
        ValidationUtils.ifNullOrEmpty(email, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "email");

        if (isUserEmailExist(email)) {
            ExceptionFactory.throwServiceException(ErrorType.CANNOT_CREATE_USER_EMAIL_ALREADY_EXIST, email);
        }

        User user = new User(firstName.trim(),
                lastName.trim(),
                password.encode(),
                MEN.equals(sex) ? Gender.M : Gender.W,
                studyGroup.trim(),
                birthday,
                email.trim(),
                UUID.randomUUID().toString(),
                Boolean.FALSE,
                Boolean.FALSE);

        return userBean.createUser(user);
    }

    public User changeUserProfile(Long userId, String firstName, String lastName, Password password, Gender sex,
                                  String studyGroup, Date birthday, String email) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(firstName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "first name");
        ValidationUtils.ifNullOrEmpty(lastName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "last name");
        ValidationUtils.ifNullOrEmpty(studyGroup, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "study group");
        ValidationUtils.ifNullOrEmpty(email, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "email");


        User user = userBean.getUserById(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPasswordEncoded(password.encode());
            user.setGender(sex);
            user.setBirthDate(birthday);
            user.setStudyGroup(studyGroup);
            user.setEmail(email);

        return userBean.updateUser(user);
    }

    public User changeUserProfile(Long userId, String firstName, String lastName, String password, Gender sex,
                                  String studyGroup, Date birthday, String email) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(firstName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "first name");
        ValidationUtils.ifNullOrEmpty(lastName, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "last name");
        ValidationUtils.ifNullOrEmpty(studyGroup, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "study group");
        ValidationUtils.ifNullOrEmpty(email, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "email");


        User user = userBean.getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setGender(sex);
        user.setBirthDate(birthday);
        user.setStudyGroup(studyGroup);
        user.setEmail(email);

        return userBean.updateUser(user);
    }

    public boolean isUserEmailExist(String email) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(email, ErrorType.CANNOT_BE_NULL_OR_EMPTY, email);

        return userDAO.isUserEmailExists(email);
    }

}
