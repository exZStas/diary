package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.common.utils.DateHelper;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.*;

import java.util.Date;
//import java.util.GregorianCalendar;

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

    private SimpleEventHandler registerPatient;


    @Inject
    public RegistrationView() {
        setWidget(uiBinder.createAndBindUi(this));
        birthDate.setDateMin(new Date(90, 0, 1));
        birthDate.setDateMax(new Date());
        firstName.addValidator(new BlankValidator<String>("Please, provide your first name!"));
        lastName.addValidator(new BlankValidator<String>("Please, provide your last name!"));
        studyGroup.addValidator(new BlankValidator<String>("Please, provide study group!"));
        email.addValidator(new BlankValidator<String>("Please, provide your email!"));
        repeatPassword.addValidator(new BlankValidator<String>("Please, repeat password!"));
        password.addValidator(new BlankValidator<String>("Please, provide a password!"));
    }

    @UiHandler("men")
    protected void menClick(ClickEvent event){
        if (!men.getValue()){
            men.setValue(false);
            woman.setValue(true);
        } else {
            men.setValue(true);
            woman.setValue(false);
        }
    }

    @UiHandler("woman")
    protected void womanClick(ClickEvent event){
        if (!woman.getValue()){
            woman.setValue(false);
            men.setValue(true);
        } else {
            woman.setValue(true);
            men.setValue(false);
        }
    }

    @UiHandler("btnAccept")
    protected void btnAcceptClick(ClickEvent event){
        if(!firstName.validate() || !lastName.validate() || !studyGroup.validate() || !email.validate()){
            return;
        }
        if(!password.getText().equals(repeatPassword.getText())){
            return;
        }
        registerPatient.onEvent();
    }

    @UiHandler("btnDecline")
    protected void btnDeclineClick(ClickEvent event){

    }

    @Override
    public String getFirstName(){
        return firstName.getText();
    }

    @Override
    public String getLastName(){
        return lastName.getText();
    }

    @Override
    public Date getBirthDay(){
        return birthDate.getDate();
    }

    @Override
    public Boolean getGender() {
        return men.getValue() ? men.getValue() : false;
    }

    @Override
    public String getStudyGroup(){
        return studyGroup.getText();
    }

    @Override
    public String getPassword(){
        return password.getText();
    }

    @Override
    public String getEmail(){
        return email.getText();
    }

    @Override
    public void addBtnDeclineClick(ClickHandler clickHandler){
        btnDecline.addClickHandler(clickHandler);
    }

    @Override
    public void registerPatientHandler(SimpleEventHandler handler){
        registerPatient = handler;
    }




}
