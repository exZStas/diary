package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Sticker;
import com.vm62.diary.common.session.UserSessionHelper;
import com.vm62.diary.frontend.client.service.EventService;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import com.vm62.diary.integration.server.assembler.EventDTOAssembler;

import java.util.*;

/**
 * Created by Ира on 18.12.2016.
 */

@Singleton
public class EventServiceImpl extends RemoteServiceServlet implements EventService{

    @Inject
    private EventBean eventBean;
    @Inject
    private UserSessionHelper userSessionHelper;

    @Override
    public EventDTO create(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                           Long duration, String sticker) throws ServiceException {
        eventBean.createEvent(userSessionHelper.getUserId(), name, description, category, start_time, end_time, complexity, duration, sticker);
        return null;
    }

    @Override
    public EventDTO update(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                           Long duration, String sticker) throws ServiceException {
        Event event = eventBean.createEvent(userSessionHelper.getUserId(), name, description, category, start_time, end_time, complexity, duration, sticker);
        eventBean.updateEvent(event);
        return null;
    }

    @Override
    public EventDTO getEvent(Long id) throws ServiceException{
        Event event = eventBean.getEventById(id);
        return new EventDTOAssembler().mapEntityToDTO(event);
    }

    @Override
    public List<EventDTO> getEventsByDayForUser(Date day) throws ServiceException{
        List<EventDTO> eventsByDay = new ArrayList<>();
        List<Event> events = eventBean.getEventsByDayForUser(day,userSessionHelper.getUserId());
        for (Event event:events) {
            eventsByDay.add(new EventDTOAssembler().mapEntityToDTO(event));
        }
        return eventsByDay;
    }
}
