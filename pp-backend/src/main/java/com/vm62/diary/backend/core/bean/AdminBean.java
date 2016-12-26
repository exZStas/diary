package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.AdminDAO;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.Admin;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class AdminBean {

    @Inject
    private AdminDAO adminDAO;

    @Inject
    private UserDAO userDAO;

    public Admin getAdmin(){
        return adminDAO.getAdmin();
    }

    public void banUser(Long userId) throws ServiceException {
        ValidationUtils.ifNull(userId, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "userId");

        User user = userDAO.getUserById(userId);
        if(user != null){
            user.setRegister(false);
        }
        userDAO.updateUser(user);
    }

    public void unBanUser(Long userId) throws ServiceException {
        ValidationUtils.ifNull(userId, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "userId");

        User user = userDAO.getUserById(userId);
        if(user != null){
            user.setRegister(true);
        }
        userDAO.updateUser(user);
    }


}
