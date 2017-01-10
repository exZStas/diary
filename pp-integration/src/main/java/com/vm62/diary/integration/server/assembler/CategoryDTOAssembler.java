package com.vm62.diary.integration.server.assembler;

import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.common.assembler.AbstractDTOAssembler;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;

public class CategoryDTOAssembler extends AbstractDTOAssembler<Category, CategoryDTO> {
    @Override
    public CategoryDTO mapEntityToDTO(Category entity) {
        if(entity == null){
            return null;
        }

        return new CategoryDTO(entity.getCategoryName(),
                entity.getCategoryColor());
    }
}
