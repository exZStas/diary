package com.vm62.diary.integration.server.assembler;

import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.assembler.AbstractDTOAssembler;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

/**
 * Created by Ира on 24.12.2016.
 */
public class EventDTOAssembler extends AbstractDTOAssembler<Event, EventDTO> {
    @Override
    public EventDTO mapEntityToDTO(Event entity){
        if(entity == null){
            return null;
        }
        return new EventDTO(entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getComplexity(),
                entity.getDuration(),
                entity.getSticker(),
                entity.getDoneStatus()
        );
    }
}
