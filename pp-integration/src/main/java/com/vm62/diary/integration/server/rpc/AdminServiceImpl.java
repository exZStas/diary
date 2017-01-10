package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.bean.AdminBean;
import com.vm62.diary.backend.core.bean.CategoryBean;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.entities.Admin;
import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.session.UserSessionHelper;
import com.vm62.diary.frontend.client.service.AdminService;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import com.vm62.diary.integration.server.assembler.AdminDTOAssembler;
import com.vm62.diary.integration.server.assembler.CategoryDTOAssembler;
import com.vm62.diary.integration.server.assembler.UserDTOAssembler;

import java.util.List;

@Singleton
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService{

    @Inject
    private UserBean userBean;
    @Inject
    private AdminBean adminBean;
    @Inject
    private CategoryBean categoryBean;
    @Inject
    private UserSessionHelper userSessionHelper;

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userBean.getAllUsers();
        return new UserDTOAssembler().mapEntitiesToDTOs(users);
    }

    @Override
    public void banUser(Long userId) throws ServiceException {
        adminBean.banUser(userId);
    }

    @Override
    public void unbanUser(Long userId) throws ServiceException {
        adminBean.unBanUser(userId);
    }

    @Override
    public AdminDTO getAdmin() {
        Admin admin = adminBean.getAdmin();
        return new AdminDTOAssembler().mapEntityToDTO(admin);
    }

    @Override
    public void createCategory(String categoryName, String categoryColor) throws ServiceException {
        categoryBean.createCategory(categoryName, categoryColor);
    }

    @Override
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryBean.getAllCategories();
        return new CategoryDTOAssembler().mapEntitiesToDTOs(categories);
    }
}
