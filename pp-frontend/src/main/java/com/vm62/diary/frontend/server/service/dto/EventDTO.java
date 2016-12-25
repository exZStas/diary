package com.vm62.diary.frontend.server.service.dto;

import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.common.constants.Sticker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ира on 16.12.2016.
 */
public class EventDTO  implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private Category category;
    private Boolean complexity;
    private  Long duration;
    private String sticker;
    private Status status;

    public EventDTO(Long id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                    Long duration, String sticker, Status status){
        this.id = id;
        this.name=name;
        this.category = category;
        this.description = description;
        this.startTime = start_time;
        this.endTime = end_time;
        this.duration=duration;
        this.sticker = sticker;
        this.complexity = complexity;
        this.status = status;
    }
    public EventDTO(){}

    public Long getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String descriptoin) {
        this.description = descriptoin;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getComplexity() {
        return complexity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Long getDuration() {
        return duration;
    }

    public String getSticker() {
        return sticker;
    }

    public void setComplexity(Boolean complexity) {
        this.complexity = complexity;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public Status getStatus() {
        return status;
    }
}
