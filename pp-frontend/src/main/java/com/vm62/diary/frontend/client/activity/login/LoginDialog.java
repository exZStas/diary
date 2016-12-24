package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.LoginServiceAsync;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

@Singleton
public class LoginDialog extends CDialogBox{
    interface LoginDialogUIBinder extends UiBinder<MaterialRow, LoginDialog>{
    }
    private static LoginDialogUIBinder uiBinder = GWT.create(LoginDialogUIBinder.class);

    @UiField
    MaterialTextBox email;
    @UiField
    MaterialTextBox password;
    @UiField
    MaterialButton btnLogin;
    @UiField
    MaterialButton btnRegistration;

    private NavigationManager navigationManager;
    private LoginServiceAsync loginServiceAsync;
    private NotificationManager notificationManager;
    private LoginDialog me;

    @Inject
    public LoginDialog(NavigationManager navigationManager, LoginServiceAsync loginServiceAsync, NotificationManager notificationManager){
        this.navigationManager = navigationManager;
        this.loginServiceAsync = loginServiceAsync;
        this.notificationManager = notificationManager;
        me = this;
        setWidget(uiBinder.createAndBindUi(this));
        btnRegistration.getElement().getStyle().setBackgroundColor("#ff8f00");

        email.addValidator(new BlankValidator<String>("Please provide your email"));
        password.addValidator(new BlankValidator<String>("Please provide password"));
        addEventHandlers();
    }

    @UiHandler("btnLogin")
    protected void btnLoginClick (ClickEvent event){
        if(!email.validate() && !password.validate()){
            return;
        }

        loginServiceAsync.login(email.getText(), password.getText(), new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails("Incorrect email or password!",true);
            }

            @Override
            public void onSuccess(UserDTO result) {
                me.hide();
                navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
            }
        });


    }

    @UiHandler("btnRegistration")
    protected void setBtnRegistrationClick (ClickEvent event){
        this.hide();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_REGISTRATION_ACTIVITY));
    }

    void addEventHandlers(){

    }

    public void clear(){
        email.setText("");
        email.clearErrorOrSuccess();
        password.setText("");
        password.clearErrorOrSuccess();
        email.setFocus(true);
    }

    public void showDialog(){
        clear();
        setCaptionHtml(HeaderTitle.LOGIN_PANEL.getText());
        center();
    }
}
