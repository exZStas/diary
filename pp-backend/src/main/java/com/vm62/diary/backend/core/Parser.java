package com.vm62.diary.backend.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    public Parser() {

    }

    public ArrayList<Event> parseSchedule(Document doc) {
        Calendar todayCalendar = Calendar.getInstance();
        Date today = todayCalendar.getTime();

        int weekType = getWeekType(doc);
        int currentDay = today.getDay();
        if (currentDay == 0) {
            currentDay = 7;
        }

        todayCalendar.add(Calendar.DATE, - currentDay + 1);
        Date previousMonday = todayCalendar.getTime();

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
                    long userId = 1;
                    boolean addFlag = false;
                    Calendar mondayCalendar = Calendar.getInstance();
                    mondayCalendar.setTime(previousMonday);

                    if (weekType == i) {
                        if (k >= currentDay) {
                            mondayCalendar.add(Calendar.DATE, k - 1);
                            addFlag = true;
                        }
                    } else {
                        mondayCalendar.add(Calendar.DATE, k + 7 - 1);
                        addFlag = true;
                    }

                    if (addFlag) {
                        Date classDate = mondayCalendar.getTime();
                        String[] hoursAndMinutes = classesTimes.get(j - 1).split(":");
                        classDate.setHours(Integer.valueOf(hoursAndMinutes[0]));
                        classDate.setMinutes(Integer.valueOf(hoursAndMinutes[1]));
                        Date classEndDate = new Date(classDate.getTime());
                        classEndDate.setMinutes(classEndDate.getMinutes() + 95);
                        long duration = classEndDate.getTime() - classDate.getTime();
                        Event classEvent = new Event(userId, subject.attr("title"), subjectDescription, Category.education, classDate, classEndDate, false, duration, "");
                        scheduleEvents.add(classEvent);
                    }
                }
            }
        }

        return scheduleEvents;
    }

    public int getWeekType(Document doc) {
        String scheduleState = doc.select(".schedule-current-state").text().trim();
        if (scheduleState.indexOf("нечетная") != -1) {
            return 0;
        } else {
            return 1;
        }
    }

}
