package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

@RemoteServiceRelativePath(ServletMappingConstants.USER_PROFILE_SERVICE_RELATIVE_PATH)
public interface UserProfileService extends RemoteService {
    UserDTO changeProfile(String firstName, String lastName, String password, Gender gender, String studyGroup, Date birthDay, String email,Boolean withPassword) throws ServiceException;

    UserDTO getUser()throws ServiceException;

}
