package TestDAO;

import TestRunner.TestRunner;
import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Gender;
import factory.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(TestRunner.class)
public class TestUserDAO {
    @Inject
    private UserDAO userDAO;
    @Inject
    private UserFactory userFactory;

    private User user1;

    @Before
    public void setUp(){
        user1 = userFactory.createUser("Maor", "Dingenman", Gender.M, "vm66", new Date(2016, 05, 12), "maor@mail.nl");

    }

    @Test
    public void test_GetUserById(){
        User user = userDAO.getUserById(user1.getId());
        assertEquals(user.getId(), user.getId());
    }



}
