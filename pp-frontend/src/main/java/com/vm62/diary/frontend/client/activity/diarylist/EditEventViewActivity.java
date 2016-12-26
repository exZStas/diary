package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

/**
 * Created by Ира on 26.12.2016.
 */
public class EditEventViewActivity implements BaseActivity {
    
    private static CreateEventActivity.ICreateEventView view;

    private EditEventActivityPlace place;
    @Inject
    EditEventViewActivity(CreateEventActivity.ICreateEventView view)
    {
        this.view = view;
    }


    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        this.place = (EditEventActivityPlace) place;
        display.add((Widget) view);

        view.setCategory(this.place.eventDTO.getCategory());
        view.setName(this.place.eventDTO.getName());
        view.setDescription(this.place.eventDTO.getDescription());
        view.setCategory(this.place.eventDTO.getCategory());
        view.setComplexity(this.place.eventDTO.getComplexity());

    }

    @Override
    public void stop() {}

    public static class EditEventActivityPlace extends NavigationPlace{

        private EventDTO eventDTO;

        public EditEventActivityPlace(EventDTO eventDTO)
        {
            super(NavigationUrl.URL_EDIT_EVENT_ACTIVITY);
            this.eventDTO = eventDTO;
        }
    }
}
