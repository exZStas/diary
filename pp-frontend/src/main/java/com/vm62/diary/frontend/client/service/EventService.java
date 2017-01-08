package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.common.constants.Sticker;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Ира on 16.12.2016.
 */
@RemoteServiceRelativePath(ServletMappingConstants.EVENT_SERVICE_RELATIVE_PATH)
public interface EventService extends RemoteService{

    EventDTO create(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                    Long duration, String sticker) throws ServiceException;

    EventDTO update(Long id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                    Long duration, String sticker, Status status) throws ServiceException;

    EventDTO getEvent(Long id) throws ServiceException;

    List<EventDTO> getEventsByDayForUser(Date day) throws ServiceException;

    Boolean deleteEventById(Long id) throws ServiceException;

    Boolean parseSchedule(String userGroup) throws ServiceException;
}
