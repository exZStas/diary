package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import gwt.material.design.client.ui.*;

@Singleton
public class RegistrationDialog extends CDialogBox{
    interface RegistrationDialogUIBinder extends UiBinder<MaterialRow, RegistrationDialog>{}
    private static RegistrationDialogUIBinder uiBinder = GWT.create(RegistrationDialogUIBinder.class);

    @UiField
    MaterialTextBox firstName;
    @UiField
    MaterialTextBox lastName;
    @UiField
    MaterialDatePicker birthDate;
    @UiField
    MaterialTextBox email;
    @UiField
    MaterialListBox gender;
    @UiField
    MaterialTextBox studyGroup;
    @UiField
    MaterialTextBox password;
    @UiField
    MaterialTextBox repeatPassword;
    @UiField
    MaterialButton btnAccept;
    @UiField
    MaterialButton btnDecline;

    @Inject
    public RegistrationDialog() {
        setWidget(uiBinder.createAndBindUi(this));
    }




}
