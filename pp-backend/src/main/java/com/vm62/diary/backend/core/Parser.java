package com.vm62.diary.backend.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.vm62.diary.backend.core.entities.Event;
import com.vm62.diary.common.constants.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Maria.
 */
public class Parser {
    private String groupName;

    public Parser() {

    }

    public ArrayList<Event> parseSchedule(Document doc) {
        ArrayList<Event> scheduleEvents = new ArrayList<Event>();

        Elements scheduleTables = doc.select(".c-table.schedule");
        Elements classesTimesElements = scheduleTables.select(".time");
        ArrayList<String> classesTimes = new ArrayList<String>();
        String bufTime;

        for (int i = 0; i < classesTimesElements.size(); i++) {
            bufTime = classesTimesElements.get(i).text();
            if (!classesTimes.contains(bufTime)) {
                classesTimes.add(bufTime);
            }
        }

        for (int i = 0; i < scheduleTables.size(); i++) {
            Elements scheduleRows = scheduleTables.get(i).select("tr");
            for (int j = 1; j < scheduleRows.size(); j++) {
                Elements scheduleColumns = scheduleRows.get(j).select("td");
                for (int k = 1; k < scheduleColumns.size(); k++) {
                    Elements subject = scheduleColumns.get(k).select(".subject");
                    if (subject.size() == 0) {
                        continue;
                    }
                    String subjectDescription = scheduleColumns.get(k).select(".room a").text() + " " + scheduleColumns.get(k).select(".lesson-type").text() + " " + scheduleColumns.get(k).select(".group-teacher").text().trim();
                    //TODO correct duration and date
                    long userId = 1;
                    long duration = 10000;
                    Event classEvent = new Event(userId, subject.attr("title"), subjectDescription, Category.education, new Date(), new Date(), false, duration, "");
                    scheduleEvents.add(classEvent);
                }
            }
        }

        return scheduleEvents;
    }
}
