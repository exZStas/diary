package com.vm62.diary.backend.core.entities;

import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.common.constants.Sticker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.vm62.diary.common.constants.Status.active;

/**
 * Created by Ира on 16.12.2016.
 */
@Entity
@Table(name="EVENT")
public class Event implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 11)
    private Long id;

    @Column(name = "USER_ID", nullable = false, length = 11)
    private  Long user_id;

    @Column(name = "NAME", nullable = false, length = 45)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true, length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false, length = 45)
    private Category category;

    @Column(name="START_TIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_time;

    @Column(name="END_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_time;

    @Column(name="COMPLEXITY",nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean complexity;

    @Column(name = "DURATION", nullable = false, length = 11)
    private Long duration;

    @Enumerated(EnumType.STRING)
    @Column(name="DONE_STATUS",nullable = false, length = 9, columnDefinition = "active")
    private Status done_status;

    @Column(name="STICKER", nullable = false, length = 45)
    private String sticker;

    public Event(){}

    public Event(Long user_id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                 Long duration, String sticker){
        this.user_id=user_id;
        this.name=name;
        this.description=description;
        this.category=category;
        this.complexity=complexity;
        this.end_time=end_time;
        this.start_time=start_time;
        this.duration=duration;
        this.sticker = sticker;
        this.done_status = active;

    }
    public Event(Long id, Long user_id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                 Long duration, String sticker, Status status){
        this.id = id;
        this.user_id=user_id;
        this.name=name;
        this.description=description;
        this.category=category;
        this.complexity=complexity;
        this.end_time=end_time;
        this.start_time=start_time;
        this.duration=duration;
        this.sticker = sticker;
        this.done_status = status;

    }
    public Long getId(){return id;}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserById() {
        return user_id;
    }
    public void setUserById(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStartTime() {
        return start_time;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date end_time) {
        this.end_time = end_time;
    }

    public void setComplexity(Boolean complexity) {
        this.complexity = complexity;
    }

    public Boolean getComplexity() {
        return complexity;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Status getDoneStatus() {
        return done_status;
    }

    public void setDoneStatus(Status done_status) {
        this.done_status = done_status;
    }

    public void setSticker(String sticker){this.sticker=sticker;}

    public String getSticker() {
        return sticker;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category+ '\'' +
                ", complexity='" + complexity + '\'' +
                ", start_time='" + start_time+ '\'' +
                ", end_time='" + end_time+ '\'' +
                ", duration='" + duration+ '\'' +
                ", done_status='" + done_status+ '\'' +
                ", sticker='" + sticker+ '\'' +
                '}';
    }
}
