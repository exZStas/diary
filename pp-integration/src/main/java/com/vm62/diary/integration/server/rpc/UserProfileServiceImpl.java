package com.vm62.diary.integration.server.rpc;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.LoginModule;
import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.PasswordPlainText;
import com.vm62.diary.common.session.UserSessionHelper;
import com.vm62.diary.frontend.client.service.UserProfileService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import com.vm62.diary.integration.server.assembler.UserDTOAssembler;

import java.util.Date;

@Singleton
public class UserProfileServiceImpl extends RemoteServiceServlet implements UserProfileService {

    @Inject
    private UserBean userBean;
    @Inject
    private UserSessionHelper userSessionHelper;
    @Inject
    private LoginModule loginModule;

    @Override
    public UserDTO changeProfile(String firstName, String lastName, String password, Gender gender, String studyGroup, Date birthDay, String email) throws ServiceException{
        User user = loginModule.changeUserProfile(userSessionHelper.getUserId(), firstName, lastName , new PasswordPlainText(password, email), gender, studyGroup, birthDay, email);
        return null;
    }

    @Override
    public UserDTO getUser()throws ServiceException{
        User user = userBean.getUserById(userSessionHelper.getUserId());

        return new UserDTOAssembler().mapEntityToDTO(user);
    }
}
