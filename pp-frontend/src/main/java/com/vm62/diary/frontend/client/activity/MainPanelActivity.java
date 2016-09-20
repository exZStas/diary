package com.vm62.diary.frontend.client.activity;

import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * Main panel is a main view container for logged-in client.
 * It contains menu and content panel displaying selected view contents
 */
@Singleton
public class MainPanelActivity implements BaseActivity {

    @ImplementedBy(MainPanelView.class)
    public interface IMainPanelView extends IsWidget {
        HasWidgets getMainContentPanel();
        void addHeaderTitleClickHandler(ClickHandler handler);

    }

    private IMainPanelView view;

    @Inject
    public MainPanelActivity(IMainPanelView view) {
        this.view = view;
        addEventListeners();
    }

    private void addEventListeners() {
        Window.addWindowClosingHandler(new Window.ClosingHandler() {
            @Override
            public void onWindowClosing(Window.ClosingEvent event) {
                event.setMessage("Back button blocker!");
            }
        });
    }



    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);

    }



    @Override
    public void stop() {
        //this method run when current activity goes stop
    }


}
