package factory;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.PasswordEncoded;

import javax.persistence.EntityManager;
import java.util.Date;

public class UserFactory {
    @Inject
    private EntityManager em;

    public User createUser(String firstName, String lastName, String password, Gender gender, String studyGroup, Date birthDay, String email, String registrationId, Boolean isRegister, Boolean isMailSent){
        User user = new User(firstName, lastName, new PasswordEncoded(password), gender, studyGroup, birthDay, email, registrationId, isRegister, isMailSent);
        em.persist(user);
        em.flush();
        return user;
    }

}
