package TestParser;

import com.vm62.diary.backend.core.Parser;
import com.vm62.diary.backend.core.entities.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maria.
 */
public class TestParser {
    @Test
    public void testParseHTML() {
        String scheduleURL = "http://rasp.tpu.ru/view.php?for=8ВМ62&weekType=1";
        Document doc;
        ArrayList<Event> scheduleEvents;
        try {
            doc = Jsoup.connect(scheduleURL).get();
            Parser htmlParser = new Parser();
            scheduleEvents = htmlParser.parseSchedule(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
