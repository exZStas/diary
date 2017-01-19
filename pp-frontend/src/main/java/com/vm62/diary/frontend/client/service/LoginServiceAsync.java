package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.server.service.dto.DateDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

public interface LoginServiceAsync {
    void login(String email,String password, AsyncCallback<UserDTO> async);

    void registration(String firstName, String lastName, String password,String gender, String studyGroup, Date birthDay, String email, AsyncCallback<UserDTO> async);

    void logOut(AsyncCallback<Void> async);
}
