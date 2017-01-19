package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.impl.Validation;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.Password;
import com.vm62.diary.common.password.PasswordEncoded;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.UserProfileServiceAsync;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;

/**
 * Created by Ира on 24.12.2016.
 */
@Singleton
public class ChangeProfileActivity implements BaseActivity {
    @ImplementedBy(ChangeProfileView.class)
    public interface IChangeProfileView extends IsWidget {

        String getFirstName();

        String getLastName();

        Date getBirthDay();

        Gender getGender();

        String getStudyGroup();

        String getEmail();
        String getPassword();
        Boolean getYes();

        void setFirstName(String firstName);
        void setLastName(String lastName);
        void setBirthDay(Date birthDay);
        void setGender(Gender gender);
        void setStudyGroup(String studyGroup);
        void setEmail(String email);
        void checkPassword(String password);
        Boolean validateForm();

        void addAcceptButtonClickHandler (ClickHandler handler);

    }

    private IChangeProfileView view;
    private NotificationManager notificationManager;
    private UserProfileServiceAsync userProfileServiceAsync;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);
    private DiaryListActivity.IDiaryListView diaryListView;
    private NavigationManager navigationManager;

    @Inject
    ChangeProfileActivity(IChangeProfileView view, NotificationManager notificationManager, NavigationManager navigationManager,
                          UserProfileServiceAsync userProfileServiceAsync,DiaryListActivity.IDiaryListView diaryListView){
        this.view = view;
        this.diaryListView = diaryListView;
        this.notificationManager = notificationManager;
        this.userProfileServiceAsync = userProfileServiceAsync;
        this.navigationManager = navigationManager;
        addEventHandler();
        addEventHandlers();
    }

    public void addEventHandlers() {
        view.addAcceptButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (view.validateForm())
                userProfileServiceAsync.changeProfile(view.getFirstName(),view.getLastName(),view.getPassword(),view.getGender(),
                        view.getStudyGroup(),view.getBirthDay(),view.getEmail(),view.getYes(), new AsyncCallback<UserDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails(constants.errorProfileWasNotChanged());
                            }

                            @Override
                            public void onSuccess(UserDTO result) {
                                notificationManager.showInfoPopup(constants.successProfileWasChanged());
                                diaryListView.setUserName(result.getFirstName()+result.getLastName());
                                diaryListView.setUserPicture(Gender.valueOf(result.getGender()));
                                diaryListView.setUserGroup(result.getStudyGroup());
                                navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
                            }
                        });
            }
        });
    }
    public void addEventHandler(){
                userProfileServiceAsync.getUser(new AsyncCallback<UserDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorProfileIsNotAvailable());
                    }

                    @Override
                    public void onSuccess(UserDTO result) {
                        view.setFirstName(result.getFirstName());
                        view.setLastName(result.getLastName());
                        view.setBirthDay(result.getBirthday());
                        view.setGender(Gender.valueOf(result.getGender()));
                        view.setStudyGroup(result.getStudyGroup());
                        view.setEmail(result.getEmail());
                        view.checkPassword(result.getPassword());
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
