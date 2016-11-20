import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
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

public class TestBean {
    @InjectMocks
    private UserBean userBean;
    @Mock
    private UserDAO userDAO;

    private User user1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user1 = new User("maor", "dingenman", new PasswordEncoded("password"),Gender.M, "vm66", new Date(2016, 05, 12), "fckthis@mail.nl", UUID.randomUUID().toString(), Boolean.FALSE);
        user1.setId(12L);
    }

    @Test
    public void test_GetUserById() {
        //given
        Long id = 12L;
        when(userDAO.getUserById(id)).thenReturn(user1);
        //when
        User user = userBean.getUserById(id);
        //then
        verify(userDAO, times(1)).getUserById(id);
        assertEquals(user.getId(), user1.getId());
    }
}
