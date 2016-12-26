package com.vm62.diary.frontend.client.activity.admin;


import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.service.AdminServiceAsync;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.List;

@Singleton
public class AdminHomeActivity implements BaseActivity {

    @ImplementedBy(AdminHomeView.class)
    interface IAdminHomeView extends IsWidget{
        void setUserTable(List<UserDTO> users);
    }

    private IAdminHomeView view;
    private NavigationManager navigationManager;
    private AdminServiceAsync adminServiceAsync;
    private NotificationManager notificationManager;

    @Inject
    public AdminHomeActivity(IAdminHomeView view, NavigationManager navigationManager, AdminServiceAsync adminServiceAsync,
                             NotificationManager notificationManager){
        this.view = view;
        this.navigationManager = navigationManager;
        this.adminServiceAsync = adminServiceAsync;
        this.notificationManager = notificationManager;
        addEventHandlers();
    }

    public void addEventHandlers(){
        adminServiceAsync.getUsers(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails("Can't retrieve users from database!");
            }

            @Override
            public void onSuccess(List<UserDTO> result) {
                view.setUserTable(result);
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
