package com.vm62.diary.integration.server.assembler;

import com.vm62.diary.backend.core.entities.Admin;
import com.vm62.diary.common.assembler.AbstractDTOAssembler;
import com.vm62.diary.frontend.server.service.dto.AdminDTO;

public class AdminDTOAssembler extends AbstractDTOAssembler<Admin, AdminDTO> {
    @Override
    public AdminDTO mapEntityToDTO(Admin entity) {
        if(entity == null){
            return null;
        }
        return new AdminDTO(
                entity.getAdminName(),
                entity.getPassword().getAsString()
        );
    }
}
