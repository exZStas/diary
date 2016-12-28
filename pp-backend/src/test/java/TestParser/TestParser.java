package TestParser;

import com.vm62.diary.backend.core.Parser;
import com.vm62.diary.backend.core.entities.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maria.
 */
public class TestParser {
    private Document scheduleHTML;
    private Parser scheduleParser;
    private String actualTitle = "Расписание /  ТПУ";
    private int actualWeekType = 1;
    @Before
    public void setUp(){
        try {
            scheduleHTML = Jsoup.parse(new File("src/test/java/TestParser/schedule.html"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        scheduleParser = new Parser();
    }
    @Test
    public void testGetTitle() {
        String title = scheduleParser.getTitle(scheduleHTML);
        assertEquals(actualTitle, title);
    }
    @Test
    public void testGetWeekType() {
        int weekType = scheduleParser.getWeekType(scheduleHTML);
        assertEquals(actualWeekType, weekType);
    }
    @Test
    public void testGetClassesTimes() {
        ArrayList<String> classesTimes = scheduleParser.getClassesTimes(scheduleHTML);
        assertEquals(classesTimes.size(), 5);
        assertEquals(classesTimes.get(0), "08:30");
        assertEquals(classesTimes.get(4), "16:10");
    }
}
