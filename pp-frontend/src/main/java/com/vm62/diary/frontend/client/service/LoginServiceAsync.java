package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

public interface LoginServiceAsync {
    void login(String email, AsyncCallback<UserDTO> async);

}
