package TestDAO;

import TestRunner.TestRunner;
import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.EventDAO;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Gender;
import factory.EventFactory;
import factory.UserFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


/**
 * Created by Ира on 18.12.2016.
 */

@RunWith(TestRunner.class)
public class TestEventDAO {
    @Inject
    private EventDAO eventDAO;
    @Inject
    private EventFactory eventFactory;
    @Inject
    private UserFactory userFactory;

    private Event event1;
    private Event event2;
    private Event event3;
    private User user1, user2, user3;
    private String uid1, uid2,uid3;
    private String email = "maor@mail.nl";
    private String email2 = "aadan@mail.nl";
    private String email3 = "fox@mail.nl";
    private Date start_time1 = new Date(116,12,18,17,00);
    private Date end_time1 = new Date(116,12,18,19,30);
    private Date start_time2 = new Date(116,12,18,17,00);
    private Date end_time2 = new Date(116,12,19,19,30);
    private Date start_time3 = new Date(116,12,20,17,00,30);
    private Date end_time3 = new Date(116,12,20,20,30,00);

    @Before
    public void setUp() {
        uid1 = UUID.randomUUID().toString();
        uid2 = UUID.randomUUID().toString();
        uid3 = UUID.randomUUID().toString();
        user1 = userFactory.createUser("Maor", "Dingenman", "password1",Gender.M, "vm66", new Date(2016, 05, 12), email, uid1, Boolean.TRUE, Boolean.TRUE);
        user2 = userFactory.createUser("Beedan", "J.C.P.D", "password1", Gender.M, "vm27", new Date(2016, 05, 12), email2, uid2, Boolean.TRUE, Boolean.TRUE);
        user3 = userFactory.createUser("Geedan", "Walker", "password1", Gender.M, "vm27", new Date(2016, 05, 12), email3, uid3, Boolean.FALSE, Boolean.FALSE);
        event1 = eventFactory.createEvent(user1.getId(),"study","bla-bla", Category.education, start_time1, end_time1, false, end_time1.getTime()-start_time1.getTime(),false);
        event2 = eventFactory.createEvent(user1.getId(),"study","", Category.education, start_time2, end_time2, true, end_time2.getTime()-start_time2.getTime(),false);
        event3 = eventFactory.createEvent(user1.getId(),"fitness","pilates", Category.sport, start_time3, end_time3, false, end_time3.getTime()-start_time3.getTime(),true);
    }

    @Test
    public void test_GetEventById(){
        Event event = eventDAO.getEventById(event1.getId());
        assertEquals(event.getId(), event1.getId());
        //assertEquals(event.getId(), user1.getId());
    }
    @Test
    public void test_UpdateEvent(){
        Event event = event1;
        event.setName("trpo");
        eventDAO.updateEvent(event);
        assertEquals(event.getName(), "trpo");
    }
    @Test
    public void test_GetUserById(){
        Event event = eventDAO.getUserById(user1.getId());
        assertEquals(event.getUserById(), event1.getUserById());
        assertEquals(event.getUserById(), user1.getId());
    }
    @Test
    public void test_GetEventByCategory(){
        List<Event> events = eventDAO.getEventByCategory(Category.education);
        assertReflectionEquals(events, Arrays.asList(event1, event2), ReflectionComparatorMode.LENIENT_ORDER);

    }
    @Test
    public void test_GetEventByComplexity(){
        List<Event> events = eventDAO.getEventByComplexity(false);
        assertReflectionEquals(events, Arrays.asList(event1, event3), ReflectionComparatorMode.LENIENT_ORDER);
    }
    @Test
    public void test_GetEventByDuration(){
        Long l = end_time1.getTime()-start_time1.getTime();
        List<Event> events = eventDAO.getEventByDuration(l);
        assertReflectionEquals(events, Arrays.asList(event1), ReflectionComparatorMode.LENIENT_ORDER);
    }
    @Test
    public void test_GetEventByDoneStatus(){
        List<Event> events = eventDAO.getEventByDoneStatus(false);
        assertReflectionEquals(events, Arrays.asList(event1,event2), ReflectionComparatorMode.LENIENT_ORDER);
    }

}
