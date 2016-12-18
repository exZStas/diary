package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.client.service.EventService;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.Date;

/**
 * Created by Ира on 18.12.2016.
 */
@Singleton
public class EventServiceImpl extends RemoteServiceServlet implements EventService{
    @Override
    public EventDTO create(String name, String type, Date start, Date end, int duration, int userID) throws ServiceException {
        return null;
    }
}
