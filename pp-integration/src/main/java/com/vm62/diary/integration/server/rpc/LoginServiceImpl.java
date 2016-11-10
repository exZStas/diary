package com.vm62.diary.integration.server.rpc;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.service.LoginService;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

@Singleton
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }
}
