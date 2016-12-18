package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
@RemoteServiceRelativePath(ServletMappingConstants.EVENT_SERVICE_RELATIVE_PATH)
public interface EventService extends RemoteService{

    EventDTO create(String name, String type, Date start, Date end, int duration, int userID) throws ServiceException;
}
