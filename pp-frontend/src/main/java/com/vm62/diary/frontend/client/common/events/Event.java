package com.vm62.diary.frontend.client.common.events;

import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.server.service.dto.DateDTO;

import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
public class Event {
    public String name;
    public Date start;
    public Date end;
    public Date endDate;
    public Category category;
    public  int duration;
    public int userID;

}
