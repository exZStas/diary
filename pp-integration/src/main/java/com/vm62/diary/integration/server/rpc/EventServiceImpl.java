package com.vm62.diary.integration.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Sticker;
import com.vm62.diary.frontend.client.service.EventService;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.Date;

/**
 * Created by Ира on 18.12.2016.
 */

@Singleton
public class EventServiceImpl extends RemoteServiceServlet implements EventService{

    @Inject
    private EventBean eventBean;

    @Override
    public EventDTO create(String name, String description, Category category, Date startTime, Date endTime, Boolean complexity,
                           Long duration, String sticker) throws ServiceException {
        eventBean.createEvent(name, description, category, startTime, endTime, complexity, duration, sticker);
        return null;
    }
}
