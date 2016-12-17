package com.vm62.diary.frontend.server.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
public class EventDTO  implements Serializable {
    private String name;
    private Date start;
    private Date end;
    private String type;
    private  int duration;
    private int userID;

    public EventDTO(String name, String type, Date start, Date end, int duration, int userID){
        this.name=name;
        this.type = type;
        this.start = start;
        this.end = end;
        this.duration=duration;
        this.userID = userID;
    }
    public EventDTO(){}
}
