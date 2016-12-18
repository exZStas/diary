package com.vm62.diary.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
public interface EventServiceAsync {

    void create(String name,String type, Date start, Date end, int duration, AsyncCallback<EventDTO> async);
}
