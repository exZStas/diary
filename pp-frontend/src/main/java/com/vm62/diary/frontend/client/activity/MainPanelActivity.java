package com.vm62.diary.frontend.client.activity;

import com.vm62.diary.frontend.client.activity.login.LoginDialog;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;


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
    private LoginDialog loginDialog;
    private NavigationManager navigationManager;

    @Inject
    public MainPanelActivity(IMainPanelView view, LoginDialog loginDialog, NavigationManager navigationManager) {
        this.view = view;
        this.loginDialog = loginDialog;
        this.navigationManager = navigationManager;
        addEventListeners();
        injectCustomResources();
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
        loginDialog.showDialog();
    }



    @Override
    public void stop() {
        //this method run when current activity goes stop
    }

    private void injectCustomResources(){
        RESOURCES.style().ensureInjected();
    }


}
