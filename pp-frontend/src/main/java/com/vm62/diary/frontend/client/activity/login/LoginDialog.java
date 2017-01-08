package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
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
public class LoginDialog extends CDialogBox {

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
    private static DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    public LoginDialog(NavigationManager navigationManager, LoginServiceAsync loginServiceAsync, NotificationManager notificationManager){
        this.navigationManager = navigationManager;
        this.loginServiceAsync = loginServiceAsync;
        this.notificationManager = notificationManager;
        me = this;
        setWidget(uiBinder.createAndBindUi(this));
        btnRegistration.getElement().getStyle().setBackgroundColor("#ff8f00");

        email.addValidator(new BlankValidator<String>(constants.enterEmail()));
        password.addValidator(new BlankValidator<String>(constants.enterPassword()));
        addEventHandlers();
    }

    @UiHandler("btnLogin")
    protected void btnLoginClick (ClickEvent event){
        onLoginAttempt();
    }

    @UiHandler("btnRegistration")
    protected void setBtnRegistrationClick (ClickEvent event){
        this.hide();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_REGISTRATION_ACTIVITY));
    }

    protected void onLoginAttempt() {
        if(!email.validate() && !password.validate()){
            return;
        }

        loginServiceAsync.login(email.getText(), password.getText(), new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails(constants.errorIncorrectEmailOrPassword());
            }

            @Override
            public void onSuccess(UserDTO result) {
                if(result != null){
                    me.hide();
                    navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
                } else {
                    notificationManager.showInfoPopup(constants.errorUnknownError());
                }
            }
        });
    }

    void addEventHandlers(){
        password.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    onLoginAttempt();
                }
            }
        });
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
