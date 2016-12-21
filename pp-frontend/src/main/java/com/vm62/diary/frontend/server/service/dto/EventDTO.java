package com.vm62.diary.frontend.server.service.dto;

import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Sticker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
public class EventDTO  implements Serializable {
    private String name;
    private String descriptoin;
    private Date startTime;
    private Date endTime;
    private Category category;
    private Boolean complexity;
    private  Long duration;
    private String sticker;

    public EventDTO(String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                    Long duration, String sticker){
        this.name=name;
        this.category = category;
        this.descriptoin = description;
        this.startTime = start_time;
        this.endTime = end_time;
        this.duration=duration;
        this.sticker = sticker;
        this.complexity = complexity;
    }
    public EventDTO(){}

}
