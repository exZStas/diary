package factory;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.PasswordEncoded;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by Ира on 18.12.2016.
 */
public class EventFactory {
    @Inject
    private EntityManager em;

    public Event createEvent(Long user_id, String name, String description, Category category, Date start_time, Date end_time, Boolean complexity,
                             Long duration, Boolean done_status) {
        Event event = new Event(user_id, name, description, category, start_time, end_time, complexity, duration, done_status);
        em.persist(event);
        em.flush();
        return event;
    }
}
