package TestBean;

import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.dao.EventDAO;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.PasswordEncoded;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ира on 23.12.2016.
 */
public class TestEventBean {
    @InjectMocks
    private EventBean eventBean;
    @Mock
    private EventDAO eventDAO;

    private Event event1;
    private Date start_time1 = new Date(116,12,18,17,00);
    private Date end_time1 = new Date(116,12,18,19,30);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        event1 = new Event(1L,"my","my event", Category.education, start_time1, end_time1, false, end_time1.getTime()-start_time1.getTime(), "difficult");
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
}
