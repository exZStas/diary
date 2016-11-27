package TestDAO;

import TestRunner.TestRunner;
import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.UserDAO;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Gender;
import factory.UserFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(TestRunner.class)
public class TestUserDAO {
    @Inject
    private UserDAO userDAO;
    @Inject
    private UserFactory userFactory;

    private User user1;
    private User user2;
    private User user3;
    private String uid;
    private String uid2;
    private String uid3;
    private String email = "maor@mail.nl";
    private String email2 = "aadan@mail.nl";
    private String email3 = "fox@mail.nl";

    @Before
    public void setUp(){
        uid = UUID.randomUUID().toString();
        uid2 = UUID.randomUUID().toString();
        uid3 = UUID.randomUUID().toString();
        user1 = userFactory.createUser("Maor", "Dingenman", "password1",Gender.M, "vm66", new Date(2016, 05, 12), email, uid, Boolean.TRUE, Boolean.TRUE);
        user2 = userFactory.createUser("Beedan", "J.C.P.D", "password1", Gender.M, "vm27", new Date(2016, 05, 12), email2, uid2, Boolean.TRUE, Boolean.TRUE);
        user3 = userFactory.createUser("Geedan", "Walker", "password1", Gender.M, "vm27", new Date(2016, 05, 12), email3, uid3, Boolean.FALSE, Boolean.FALSE);

    }

    @Test
    public void test_GetUserById(){
        User user = userDAO.getUserById(user1.getId());
        assertEquals(user.getId(), user.getId());
        assertEquals(user.isRegister(), Boolean.TRUE);
        assertEquals(user.getRegistrationId(), uid);
    }

    @Test
    public void test_GetUserByEmail(){
        User user = userDAO.getUserByEmail(email);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void test_UpdateUser(){
        User user = user1;
        String newEmail = "ivan@rus.com";
        user.setEmail(newEmail);
        userDAO.updateUser(user);
        assertEquals(user.getEmail(), newEmail);
    }

    @Test
    public void test_GetUserByRegistrationId(){
        User user = userDAO.getUserByRegistrationId(uid);
        assertEquals(user.getRegistrationId(), uid);
    }

    @Test
    public void test_GetUsersByRegistrationStatus_TRUE(){
        List<User> users = userDAO.getUsersByRegistrationStatus(Boolean.TRUE);
        assertReflectionEquals(users, Arrays.asList(user1, user2), ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void test_GetUsersByRegistrationStatus_FALSE(){
        List<User> users = userDAO.getUsersByRegistrationStatus(Boolean.FALSE);
        assertReflectionEquals(users, Arrays.asList(user3), ReflectionComparatorMode.LENIENT_ORDER);
    }



}
