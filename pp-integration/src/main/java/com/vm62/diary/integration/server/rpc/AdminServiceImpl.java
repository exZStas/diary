package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.bean.AdminBean;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.entities.Admin;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.frontend.client.service.AdminService;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import com.vm62.diary.integration.server.assembler.AdminDTOAssembler;
import com.vm62.diary.integration.server.assembler.UserDTOAssembler;

import java.util.List;

@Singleton
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService{

    @Inject
    private UserBean userBean;
    @Inject
    private AdminBean adminBean;

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userBean.getAllUsers();
        return new UserDTOAssembler().mapEntitiesToDTOs(users);
    }

    @Override
    public AdminDTO getAdmin() {
        Admin admin = adminBean.getAdmin();
        return new AdminDTOAssembler().mapEntityToDTO(admin);
    }
}
