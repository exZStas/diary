package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gwt.material.design.client.ui.*;

public class RegistrationView extends Composite implements RegistrationActivity.IRegistrationView{
    private static RegistrationDialogUIBinder uiBinder = GWT.create(RegistrationDialogUIBinder.class);
    interface RegistrationDialogUIBinder extends UiBinder<MaterialPanel, RegistrationView>{

    }

    @UiField
    protected MaterialTextBox firstName;
    @UiField
    protected MaterialTextBox lastName;
    @UiField
    protected MaterialDatePicker birthDate;
    @UiField
    protected MaterialTextBox email;
    @UiField
    protected MaterialCheckBox men;
    @UiField
    protected MaterialCheckBox woman;
    @UiField
    protected MaterialTextBox studyGroup;
    @UiField
    protected MaterialTextBox password;
    @UiField
    protected MaterialTextBox repeatPassword;
    @UiField
    protected MaterialButton btnAccept;
    @UiField
    protected MaterialButton btnDecline;

    @Inject
    public RegistrationView() {
        setWidget(uiBinder.createAndBindUi(this));
    }




}
