package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.LoginServiceAsync;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

@Singleton
public class RegistrationActivity implements BaseActivity {
    private final static String MEN = "men";
    private final static String WOMEN = "women";

    @ImplementedBy(RegistrationView.class)
    public interface IRegistrationView extends IsWidget {
        String getFirstName();

        String getLastName();

        Date getBirthDay();

        Boolean getGender();

        String getStudyGroup();

        String getPassword();

        String getEmail();

        void addBtnDeclineClick(ClickHandler clickHandler);

        void registerPatientHandler(SimpleEventHandler handler);
    }

    private IRegistrationView view;
    private LoginServiceAsync loginServiceAsync;
    private NotificationManager notificationManager;
    private NavigationManager navigationManager;

    @Inject
    RegistrationActivity(IRegistrationView view, LoginServiceAsync loginServiceAsync, NotificationManager notificationManager,
                         NavigationManager navigationManager) {

        this.view = view;
        this.loginServiceAsync = loginServiceAsync;
        this.notificationManager = notificationManager;
        this.navigationManager = navigationManager;
        addEventHandlers();

    }

    public void addEventHandlers() {
        view.addBtnDeclineClick(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_MAIN));
            }
        });

        view.registerPatientHandler(new SimpleEventHandler() {
            @Override
            public void onEvent() {
                loginServiceAsync.registration(view.getFirstName(), view.getLastName(), view.getPassword(), view.getGender() ? MEN : WOMEN,
                        view.getStudyGroup(), view.getBirthDay(), view.getEmail(), new AsyncCallback<UserDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails("Registration was failed!");
                            }

                            @Override
                            public void onSuccess(UserDTO result) {
                                notificationManager.showInfoPopup("Registration successful!");
                            }
                        });
            }
        });
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}
