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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
        String scheduleURL = null;
        try {
            scheduleURL = "http://rasp.tpu.ru/view.php?for=" + URLEncoder.encode(userGroup,"utf-8") + "&weekType=1";}
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try{
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
    public Date scheduleUpdate(String userGroup, Date startDay) throws ServiceException {
        Parser scheduleParser = new Parser();
        String scheduleURL = null;
        Date endDay = startDay;
        try {
            scheduleURL = "http://rasp.tpu.ru/view.php?for=" + URLEncoder.encode(userGroup,"utf-8") + "&weekType=1";}
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<Event> scheduleEvents = scheduleParser.parseSchedule(Jsoup.connect(scheduleURL).get());
            if (scheduleEvents.isEmpty()) return startDay;

            endDay = scheduleEvents.get(scheduleEvents.size()-1).getEndTime();

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startDay);

            //Delete old classes
            while (calendar.getTime().before(endDay)) {
                for (EventDTO odlEvent:getEventsByDayForUser(calendar.getTime())) {
                    if (odlEvent.getCategory().equals(Category.classes)) deleteEventById(odlEvent.getId());
                }
                calendar.add(Calendar.DATE, 1);
            }
            //Set new classes
            for (Event event: scheduleEvents){
                event.setUserById(userSessionHelper.getUserId());
                eventBean.saveEven(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return endDay;
    }

    @Override
    public Map<Date, Date> findFreeTime(Date date,Date endTime) throws ServiceException {
        List<Event> events = eventBean.getEventsBetweenDaysForUser(date,endTime,userSessionHelper.getUserId());

        Date nextDate = new Date(date.getTime()+ 24*60*59*1000);
        Map<Date, Date> freeTime = new HashMap<>();
        Date buffer = date;

        for (Event event:events) {
            if (event.getStartTime().after(date))
            freeTime.put(buffer,event.getStartTime());
            buffer = event.getEndTime();
        }
        freeTime.put(buffer,endTime);
        return freeTime;
    }


    @Override
    public List<EventDTO> getEventsByDayForUser(Date day) throws ServiceException {
        List<Event> events = eventBean.getEventsByDayForUser(day, userSessionHelper.getUserId());
        return new EventDTOAssembler().mapEntitiesToDTOs(events);
    }



}
