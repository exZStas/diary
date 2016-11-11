package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

@RemoteServiceRelativePath(ServletMappingConstants.LOGIN_SERVICE_RELATIVE_PATH)
public interface LoginService extends RemoteService {

    UserDTO login (String email);

}
