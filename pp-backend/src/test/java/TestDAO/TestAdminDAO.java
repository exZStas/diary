package TestDAO;

import TestRunner.TestRunner;
import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.AdminDAO;
import com.vm62.diary.backend.core.entities.Admin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(TestRunner.class)
public class TestAdminDAO {

    @Inject
    private AdminDAO adminDAO;

    private String passwordEncoded;
    private String adminName;

    @Before
    public void setUp(){
        passwordEncoded = "0dea849e9fe91b89b411440f4cc0ae0f";
        adminName = "admin";

    }

    @Test
    public void test_GetAdmin(){
        Admin admin = adminDAO.getAdmin();
        assertEquals(admin.getAdminName(), adminName);
        assertEquals(admin.getPassword().getAsString(), passwordEncoded);
    }

    @After
    public void tearDown(){

    }


}
