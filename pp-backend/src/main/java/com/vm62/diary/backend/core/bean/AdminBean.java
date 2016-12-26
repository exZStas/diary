package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.AdminDAO;
import com.vm62.diary.backend.core.entities.Admin;
import com.vm62.diary.common.ServiceException;

@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class AdminBean {

    @Inject
    private AdminDAO adminDAO;

    public Admin getAdmin(){
        return adminDAO.getAdmin();
    }
}
