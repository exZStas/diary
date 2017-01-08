package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.Parser;
import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.common.session.UserSessionHelper;
import com.vm62.diary.frontend.client.service.EventService;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import com.vm62.diary.integration.server.assembler.EventDTOAssembler;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ира on 18.12.2016.
 */

@Singleton
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

    @Inject
    private EventBean eventBean;
    @Inject
    private UserSessionHelper userSessionHelper;

    @Override
    public EventDTO create(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                           Long duration, String sticker) throws ServiceException {
        Event event = eventBean.createEvent(userSessionHelper.getUserId(), name, description, category, start_time, end_time, complexity, duration, sticker);
        return new EventDTOAssembler().mapEntityToDTO(event);
    }

    @Override
    public EventDTO update(Long id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                           Long duration, String sticker, Status status) throws ServiceException {
        Event event = eventBean.updateEvent(id, userSessionHelper.getUserId(), name, description, category, start_time, end_time, complexity, duration, sticker, status);
        return new EventDTOAssembler().mapEntityToDTO(eventBean.updateEvent(event));
    }

    @Override
    public EventDTO getEvent(Long id) throws ServiceException {
        Event event = eventBean.getEventById(id);
        return new EventDTOAssembler().mapEntityToDTO(event);
    }

    @Override
    public Boolean deleteEventById(Long id) throws ServiceException {
        return eventBean.deleteEventById(id);
    }

    @Override
    public Boolean parseSchedule(String userGroup) throws ServiceException {
        Parser scheduleParser = new Parser();
        String scheduleURL = "http://rasp.tpu.ru/view.php?for=" + userGroup + "&weekType=1";
        try {
            ArrayList<Event> scheduleEvents = scheduleParser.parseSchedule(Jsoup.connect(scheduleURL).get());
            if (scheduleEvents.isEmpty()) return false;
            for (Event event: scheduleEvents){
                event.setUserById(userSessionHelper.getUserId());
                eventBean.saveEven(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<EventDTO> getEventsByDayForUser(Date day) throws ServiceException {
        List<Event> events = eventBean.getEventsByDayForUser(day, userSessionHelper.getUserId());
        return new EventDTOAssembler().mapEntitiesToDTOs(events);
    }

}
