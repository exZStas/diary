package com.vm62.diary.frontend.client.activity.admin;

import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Random;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.*;

@Singleton
public class AdminLoginDialog extends CDialogBox {
    interface AdminLoginDialogUiBinder extends UiBinder<MaterialRow, AdminLoginDialog> {
    }
    private static AdminLoginDialogUiBinder uiBinder = GWT.create(AdminLoginDialogUiBinder.class);

    @UiField
    MaterialTextBox txtUsername;

    @UiField
    MaterialTextBox txtPassword;

    @UiField
    MaterialButton btnLogin;

    @UiField
    MaterialButton btnBack;

    private NavigationManager navigationManager;
    private NotificationManager notificationManager;

    @Inject
    public AdminLoginDialog(NavigationManager navigationManager,
                            NotificationManager notificationManager) {
        this.navigationManager = navigationManager;
        this.notificationManager = notificationManager;
        setWidget(uiBinder.createAndBindUi(this));
        btnBack.getElement().getStyle().setBackgroundColor("#ff8f00");


		txtUsername.addValidator(new BlankValidator<String>("Please provide user name"));
		txtPassword.addValidator(new BlankValidator<String>("Please provide password"));
        addEventListeners();
    }

    @UiHandler("btnLogin")
    protected void btnLoginClick(ClickEvent event) {
        if(!txtUsername.validate() && !txtPassword.validate()){
            return;
        }

    }

    @UiHandler("btnBack")
    protected void btnBackClick(ClickEvent event){
        this.hide();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_MAIN));
    }

    public void clear() {
        txtUsername.setText("");
        txtUsername.clearErrorOrSuccess();
        txtPassword.setText("");
        txtPassword.clearErrorOrSuccess();
        txtUsername.setFocus(true);
    }

    public void showError(String errorMsg) {
        txtUsername.setError(errorMsg);
        txtUsername.setFocus(true);
    }

    public void showDialog() {
        clear();
        setCaptionHtml(HeaderTitle.ADMIN_LOGIN.getText());
        center();
    }

	private void addEventListeners() {
		this.addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keyCode = event.getNativeEvent().getKeyCode();
				txtUsername.clearErrorOrSuccess();
				txtPassword.clearErrorOrSuccess();
				switch (keyCode) {
					case KeyCodes.KEY_ENTER:
						btnLoginClick(null);
						break;
				}
				event.stopPropagation();
			}
		}, KeyDownEvent.getType());
	}
}