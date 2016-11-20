package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.server.service.dto.DateDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

@RemoteServiceRelativePath(ServletMappingConstants.LOGIN_SERVICE_RELATIVE_PATH)
public interface LoginService extends RemoteService {

    UserDTO login (String email);

    UserDTO registration (String firstName, String lastName, String password,String gender, String studyGroup, Date birthDay, String email) throws ServiceException;

}
