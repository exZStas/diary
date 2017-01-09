package com.vm62.diary.backend.core.bean;

import com.google.api.client.util.NullValue;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.EventDAO;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import org.apache.commons.lang.ObjectUtils;

import javax.annotation.Nullable;
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

    public Event createEvent(Long userId, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                             Long duration, String sticker) throws ServiceException{
        ifNull(userId, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "user_id");
        ifNull(name, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "name");
        ifNull(category, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "category");
        ifNull(start_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "start_time");
        ifNull(end_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "end_time");
        ifNull(duration, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "duration");

        Event event = new Event(userId, name, description, category, start_time, end_time, complexity,
                duration, sticker);

        return eventDAO.createEvent(event);
    }

    public void saveEven(Event event){
        eventDAO.createEvent(event);
    }
    public Event updateEvent(Long id, Long userId, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                             Long duration, String sticker, Status status) throws ServiceException{
        ifNull(id, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "id");
        ifNull(userId, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "user_id");
        ifNull(name, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "name");
        ifNull(category, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "category");
        ifNull(start_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "start_time");
        ifNull(end_time, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "end_time");
        ifNull(duration, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "duration");
        ifNull(status, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "status");

        Event event = new Event(id, userId, name, description, category, start_time, end_time, complexity,
                duration, sticker, status);

        return eventDAO.updateEvent(event);
    }

    @Nullable
    public Event getEventById(Long id){
        return eventDAO.getEventById(id);
    }

    public Boolean deleteEventById(Long id){
        eventDAO.deleteEvent(getEventById(id));
        if (getEventById(id) instanceof Event)
            return false;
        else return true;
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

     public List<Event> getEventsByDayForUser(Date day, Long userId) throws ServiceException{
         ifNull(day, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "day");
         ifNull(userId, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "userId");
         return eventDAO.getEventsByDayForUser(day,userId);
     }

    public List<Event> getEventsBetweenDaysForUser(Date startDay, Date endDay, Long userId) throws ServiceException{
        ifNull(startDay, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "day");
        ifNull(endDay, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "day");
        ifNull(userId, ErrorType.CANNOT_BE_NULL_OR_EMPTY, "userId");
        return eventDAO.getEventsBetweenDaysForUser(startDay, endDay,userId);
    }

}
