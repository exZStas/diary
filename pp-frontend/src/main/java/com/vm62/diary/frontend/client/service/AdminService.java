package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.List;

@RemoteServiceRelativePath(ServletMappingConstants.ADMIN_SERVICE_RELATIVE_PATH)
public interface AdminService extends RemoteService{
    List<UserDTO> getUsers();

    void banUser(Long userId) throws ServiceException;

    void unbanUser(Long userId) throws ServiceException;

    AdminDTO getAdmin();

    void createCategory(String categoryName, String categoryColor) throws ServiceException;

    List<CategoryDTO> getAllCategories();
}
