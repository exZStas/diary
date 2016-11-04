package factory;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Gender;

import javax.persistence.EntityManager;
import java.util.Date;

public class UserFactory {
    @Inject
    private EntityManager em;

    public User createUser(String firstName, String lastName, Gender gender, String studyGroup, Date birthDay, String email){
        User user = new User(firstName, lastName, gender, studyGroup, birthDay, email);
        em.persist(user);
        em.flush();
        return user;
    }

}
