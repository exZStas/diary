package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.List;

public interface AdminServiceAsync {

    void getUsers(AsyncCallback<List<UserDTO>> async);

    void getAdmin(AsyncCallback<AdminDTO> async);

    void banUser(Long userId, AsyncCallback<Void> async);

    void unbanUser(Long userId, AsyncCallback<Void> async);

    void createCategory(String categoryName, String categoryColor, AsyncCallback<Void> async);
}
