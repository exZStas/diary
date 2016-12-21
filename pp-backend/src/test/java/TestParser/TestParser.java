package TestParser;

import com.vm62.diary.backend.core.Parser;
import com.vm62.diary.backend.core.entities.Event;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Maria.
 */
public class TestParser {
    @Test
    public void testParseHTML() {
        Parser htmlParser = new Parser("8ВМ62");

        ArrayList<Event> scheduleEvents = htmlParser.parseSchedule();
    }
}
