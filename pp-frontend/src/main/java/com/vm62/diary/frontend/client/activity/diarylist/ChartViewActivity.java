package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Status;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.*;

/**
 * Created by Ира on 27.12.2016.
 */
public class ChartViewActivity implements BaseActivity {

    private DiaryListActivity.IDiaryListView view;
    private ChartViewActivityPlace place;
    private TreeMap<String,Long> dicUndone = new TreeMap<String,Long>();
    private TreeMap<String,Long> dicDone = new TreeMap<String,Long>();
    @Inject
    ChartViewActivity(DiaryListActivity.IDiaryListView view)
    {
        this.view = view;
    }


    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        if (place instanceof ChartViewActivityPlace){
            this.place = (ChartViewActivityPlace) place;
        }
        display.add((Widget) view);

        //view.setUserName(this.place.eventDTOs.get(0).getName());
        piesOfCategories(this.place.eventDTOs);
        view.setChartParameters();
        view.createPieCharts(dicUndone, dicDone);


    }
    private void piesOfCategories(List<EventDTO> eventDTOs){
        for (EventDTO event:eventDTOs) {
            if (!event.getStatus().equals(Status.done)) {
                if (dicUndone.containsKey(event.getCategory().getCategory())) {
                    dicUndone.get(event.getCategory().getCategory());
                    Long duration = (dicUndone.get(event.getCategory().getCategory()) + event.getDuration()) / 60000;
                    dicUndone.remove(event.getCategory().getCategory());
                    dicUndone.put(event.getCategory().getCategory(), duration);
                } else dicUndone.put(event.getCategory().getCategory(), event.getDuration() / 60000);
            }
            else {
                if (dicDone.containsKey(event.getCategory().getCategory())) {
                    dicDone.get(event.getCategory().getCategory());
                    Long duration = (dicDone.get(event.getCategory().getCategory()) + event.getDuration()) / 60000;
                    dicDone.remove(event.getCategory().getCategory());
                    dicDone.put(event.getCategory().getCategory(), duration);
                }else dicDone.put(event.getCategory().getCategory(), event.getDuration() / 60000);
            }
        }
    }

    @Override
    public void stop() {}

    public static class ChartViewActivityPlace extends NavigationPlace {

        private List<EventDTO> eventDTOs = new ArrayList<>();

        public ChartViewActivityPlace(List<EventDTO> eventDTOs) {
            super(NavigationUrl.URL_CHART_ACTIVITY);
            this.eventDTOs = eventDTOs;
        }

    }
}
