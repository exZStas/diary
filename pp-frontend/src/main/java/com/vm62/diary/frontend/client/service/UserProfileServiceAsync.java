package com.vm62.diary.frontend.client.service;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

public interface UserProfileServiceAsync {
    void changeProfile(String firstName, String lastName, String password, Gender gender, String studyGroup, Date birthDay, String email, AsyncCallback<UserDTO> async);

    void getUser(AsyncCallback<UserDTO> async);
}
