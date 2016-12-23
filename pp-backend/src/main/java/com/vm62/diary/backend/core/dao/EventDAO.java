package com.vm62.diary.backend.core.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

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
   /* public Event setNewDoneStatus(Boolean doneStatus, Event event){
        EntityManager em = emProvider.get();
        Event detectedEvent = em.find(Event.class, event);
        detectedEvent.setDoneStatus(doneStatus);
        Event updatedEvent = em.merge(detectedEvent);
        em.flush();
        return updatedEvent;
    }*/
    public Event getEventById(Long id) {

        EntityManager em = emProvider.get();

        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.id = :ID", Event.class);

        query.setParameter("ID", id);
        return QueryHelper.getSingleValueOrNull(query);
    }

    public Event getUserById(Long user_id){
        EntityManager em = emProvider.get();

        TypedQuery<Event> query = em.createQuery("SELECT us FROM "
                + Event.class.getName() + " us " + "WHERE us.user_id = :USER_ID", Event.class);

        query.setParameter("USER_ID", user_id);
        return QueryHelper.getSingleValueOrNull(query);
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
}
