package com.vm62.diary.integration.server.rpc;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.LoginModule;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.password.PasswordPlainText;
import com.vm62.diary.frontend.client.service.LoginService;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import com.vm62.diary.integration.server.assembler.UserDTOAssembler;
import com.vm62.diary.integration.session.UserSessionHelper;

import java.util.Date;

@Singleton
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    @Inject
    private LoginModule loginModule;

    @Inject
    private UserSessionHelper userSessionHelper;

    @Override
    public UserDTO login(String email, String password) throws ServiceException{
        User user = loginModule.authorisateUser(email, new PasswordPlainText(password, email));

        return new UserDTOAssembler().mapEntityToDTO(user);
    }

    @Override
    public UserDTO registration(String firstName, String lastName, String password,String gender, String studyGroup, Date birthDay, String email) throws ServiceException {
        User user = loginModule.createUser(firstName, lastName, new PasswordPlainText(password, email),gender, studyGroup, birthDay, email);

        return new UserDTOAssembler().mapEntityToDTO(user);
    }
}
