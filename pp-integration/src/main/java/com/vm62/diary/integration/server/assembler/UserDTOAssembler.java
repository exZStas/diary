package com.vm62.diary.integration.server.assembler;

import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.assembler.AbstractDTOAssembler;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

public class UserDTOAssembler extends AbstractDTOAssembler<User, UserDTO> {
    @Override
    public UserDTO mapEntityToDTO(User entity) {
        if(entity == null){
            return null;
        }
        return new UserDTO(entity.getFirstName(),
                entity.getLastName(),
                entity.getGender().name(),
                entity.getStudyGroup(),
                entity.getBirthDate(),
                entity.getEmail(),
                entity.getPassword().encode().getAsString()
                );
    }
}
