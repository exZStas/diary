package com.vm62.diary.backend.core.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import org.hibernate.Hibernate;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ира on 18.12.2016.
 */
@Transactional
public class EventDAO {
    @Inject
    private Provider<EntityManager> emProvider;

    public Event createEvent(Event event) {
        EntityManager em = emProvider.get();
        em.persist(event);
        em.flush();
        return event;
    }
    public Event updateEvent(Event event) {
        EntityManager em = emProvider.get();
        Event updatedEvent = em.merge(event);
        em.flush();
        return updatedEvent;
    }
    public void deleteEvent(Event event){
        EntityManager em = emProvider.get();
        em.remove(event);
        em.flush();
    }

    @Nullable
    public Event getEventById(Long id) {

        EntityManager em = emProvider.get();

        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.id = :ID", Event.class);

        query.setParameter("ID", id);
        return QueryHelper.getSingleValueOrNull(query);
    }

    public List<Event> getEventByUserId(Long user_id){
        EntityManager em = emProvider.get();

        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.user_id = :USER_ID", Event.class);

        query.setParameter("USER_ID", user_id);
        return query.getResultList();
    }
    public List<Event> getEventByStartDate(Date day){
        EntityManager em = emProvider.get();
        day.setHours(0);
        day.setMinutes(0);
        day.setSeconds(0);

        TypedQuery<Event> query = em.createQuery("SELECT ev FROM "
                + Event.class.getName() + " ev " + "WHERE ev.start_time >= :DAY", Event.class);

        query.setParameter("DAY", day, TemporalType.TIMESTAMP);
        return query.getResultList();
    }
    public List<Event> getEventByCategory(Category category){
        EntityManager em = emProvider.get();
        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.category = :CATEGORY", Event.class);

        query.setParameter("CATEGORY", category);
        return query.getResultList();
    }
    //Complexity = false - event is simple, else - complex
    public List<Event> getEventByComplexity(Boolean complexity){
        EntityManager em = emProvider.get();
        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.complexity = :COMPLEXITY", Event.class);

        query.setParameter("COMPLEXITY", complexity);
        return query.getResultList();
    }
    public List<Event> getEventByDuration(Long duration){
        EntityManager em = emProvider.get();
        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.duration = :DURATION", Event.class);

        query.setParameter("DURATION", duration);
        return query.getResultList();
    }
    public List<Event> getEventByDoneStatus(Status done_status){
        EntityManager em = emProvider.get();
        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.done_status = :DONE_STATUS", Event.class);

        query.setParameter("DONE_STATUS", done_status);
        return query.getResultList();
    }
//    public List<Event> getEventsByDayForUser(Date day, Long user_id){
//        EntityManager em = emProvider.get();
//        Date nextDay = new Date(day.getTime()+ 24*60*60*1000);
//        TypedQuery<Event> query = em.createQuery("SELECT ev FROM "
//                + Event.class.getName() + " ev " + "WHERE ev.user_id = :USER_ID AND ev.start_time BETWEEN :day AND :next_day", Event.class);
//
//        query.setParameter("day",day).setParameter("next_day",nextDay).setParameter("USER_ID", user_id);
//        return query.getResultList();
//    }
    public List<Event> getEventsByDayForUser(Date date, Long user_id){
        EntityManager em = emProvider.get();
        Date day = new Date(date.getYear(),date.getMonth(),date.getDate(),0,0,0);

        Date nextDay = new Date(day.getTime()+ 24*60*60*1000);
        TypedQuery<Event> query = em.createQuery("SELECT ev FROM "
                + Event.class.getName() + " ev " + "WHERE ev.user_id = :USER_ID AND ev.start_time >= :day AND ev.end_time < :nextDay ORDER BY ev.start_time", Event.class);

        query.setParameter("day",day, TemporalType.TIMESTAMP).setParameter("nextDay",nextDay, TemporalType.TIMESTAMP).setParameter("USER_ID", user_id);
        return query.getResultList();
    }
    public List<Event> getEventsBetweenDaysForUser(Date startDay, Date endDay, Long userId){
        EntityManager em = emProvider.get();

        TypedQuery<Event> query = em.createQuery("SELECT ev FROM "
                + Event.class.getName() + " ev " + "WHERE ev.user_id = :USER_ID AND ev.start_time >= :startDay AND ev.end_time < :endDay ORDER BY ev.start_time", Event.class);

        query.setParameter("startDay",startDay, TemporalType.TIMESTAMP).setParameter("endDay",endDay, TemporalType.TIMESTAMP).setParameter("USER_ID", userId);
        return query.getResultList();
    }

}
