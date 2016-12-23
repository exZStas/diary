package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.EventDAO;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;

import java.util.Date;
import java.util.List;

import static com.vm62.diary.common.utils.ValidationUtils.ifNull;

/**
 * Created by Ира on 18.12.2016.
 */
@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class EventBean {
    @Inject
    public EventDAO eventDAO;

    public Event createEvent(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                             Long duration, String sticker) throws ServiceException{
        ifNull(name, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "name");
        ifNull(category, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "category");
        ifNull(start_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "start_time");
        ifNull(end_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "end_time");
        ifNull(duration, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "duration");

        Event event = new Event(1L, name, description, category, start_time, end_time, complexity,
                duration, sticker);

        return eventDAO.createEvent(event);

    }
    public Event getEventById(Long id){
        return eventDAO.getEventById(id);
    }

    public Event setEventDoneStatus(Status doneStatus, Event event) throws ServiceException {
        ifNull(event, ErrorType.CANNOT_BE_NULL, "event");
        ifNull(doneStatus, ErrorType.CANNOT_BE_NULL, "done_status");
        event.setDoneStatus(doneStatus);
        return eventDAO.updateEvent(event);
    }
    public Event updateEvent(Event event) throws ServiceException {
        ifNull(event, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "event");

        return eventDAO.updateEvent(event);
    }
    public List<Event> getEventByCategory(Category category) throws ServiceException {
        ifNull(category, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "category");
        return eventDAO.getEventByCategory(category);
    }
}
