package com.vm62.diary.frontend.client.activity.login;

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
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
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

        void registerPatientHandler(SimpleEventHandler handler);
    }

    private IRegistrationView view;
    private LoginServiceAsync loginServiceAsync;
    private NotificationManager notificationManager;

    @Inject
    RegistrationActivity(IRegistrationView view, LoginServiceAsync loginServiceAsync, NotificationManager notificationManager) {

        this.view = view;
        this.loginServiceAsync = loginServiceAsync;
        this.notificationManager = notificationManager;
        addEventHandlers();

    }

    public void addEventHandlers() {
        view.registerPatientHandler(new SimpleEventHandler() {
            @Override
            public void onEvent() {
                loginServiceAsync.registration(view.getFirstName(), view.getLastName(), view.getPassword(), view.getGender() ? MEN : WOMEN,
                        view.getStudyGroup(), view.getBirthDay(), view.getEmail(), new AsyncCallback<UserDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails("Registration was failed!", true);
                            }

                            @Override
                            public void onSuccess(UserDTO result) {
                                notificationManager.showErrorPopupWithoutDetails("Registration successful!", false);
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
