package TestBean;

import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.dao.EventDAO;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.common.password.PasswordEncoded;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by Ира on 23.12.2016.
 */
public class TestEventBean {
    @InjectMocks
    private EventBean eventBean;
    @Mock
    private EventDAO eventDAO;

    private Event event1, event2, event3;
    private Date start_time1 = new Date(116,12,18,17,00);
    private Date end_time1 = new Date(116,12,18,19,30);
    private Date start_time2 = new Date(116,12,19,17,00);
    private Date end_time2 = new Date(116,12,20,19,30);
    private Date start_time3 = new Date(116,12,18,12,00);
    private Date end_time3 = new Date(116,12,18,13,30);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        event1 = new Event(1L,"my1","my event", Category.education, start_time1, end_time1, false, end_time1.getTime()-start_time1.getTime(), "difficult");
        event2 = new Event(1L,"my2","my event", Category.sport, start_time2, end_time2, false, end_time2.getTime()-start_time2.getTime(), "difficult");
        event3 = new Event(1L,"my3","my event", Category.entertainment, start_time3, end_time3, false, end_time3.getTime()-start_time3.getTime(), "difficult");
        event1.setId(12L);
    }

    @Test
    public void test_GetEventById() {
        //given
        Long id = 12L;
        when(eventDAO.getEventById(id)).thenReturn(event1);
        //when
        Event event = eventBean.getEventById(id);
        //then
        verify(eventDAO, times(1)).getEventById(id);
        assertEquals(event.getId(), event1.getId());
    }

    @Test
    public void test_deleteEventById(){
        Long id = 12L;
        Boolean res = eventBean.deleteEventById(id);
        //then
        assertEquals(res, true);
    }

    @Test
    public void test_processAddledEvents(){
        Date startDate = new Date();
        startDate.setTime(48*60*60*1000);
        Date endDate = new Date();
        endDate.setTime(24*60*60*1000);
        Event updatedEvent = new Event(1L, 1L,"someName", "some description", Category.eating,
                startDate, endDate, false, new Date(startDate.getTime() - endDate.getTime()).getTime(), "sticker", Status.undefined);

        Event event = new Event(1L, 1L,"someName", "some description", Category.eating,
                startDate, endDate, false, new Date(startDate.getTime() - endDate.getTime()).getTime(), "sticker", Status.active);

        when(eventDAO.getEventByDoneStatus(Status.active)).thenReturn(Arrays.asList(event));
        when(eventDAO.updateEvent(event)).thenReturn(updatedEvent);

        eventBean.processAddledEvents();
    }
}
