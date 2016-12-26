package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.List;

@RemoteServiceRelativePath(ServletMappingConstants.ADMIN_SERVICE_RELATIVE_PATH)
public interface AdminService extends RemoteService{
    List<UserDTO> getUsers();
    AdminDTO getAdmin();

}
