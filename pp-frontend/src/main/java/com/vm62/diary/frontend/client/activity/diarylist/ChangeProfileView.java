package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.Password;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.*;

import java.beans.Transient;
import java.util.Date;

/**
 * Created by Ира on 24.12.2016.
 */
public class ChangeProfileView extends Composite implements ChangeProfileActivity.IChangeProfileView{
    interface ChangeProfileViewUiBinder extends UiBinder<MaterialPanel, ChangeProfileView> {
    }

    private static ChangeProfileViewUiBinder ourUiBinder = GWT.create(ChangeProfileViewUiBinder.class);
    @UiField
    MaterialTextBox firstName;
    @UiField
    MaterialTextBox lastName;
    @UiField
    MaterialDatePicker birthDate;
    @UiField
    MaterialTextBox email;
    @UiField
    MaterialCheckBox men;
    @UiField
    MaterialCheckBox woman;
    @UiField
    MaterialTextBox studyGroup;
    @UiField
    MaterialCheckBox yes;
    @UiField
    MaterialCheckBox no;
    @UiField
    protected MaterialTextBox password;
    @UiField
    protected MaterialTextBox newPassword;
    @UiField
    protected MaterialTextBox repeatNewPassword;
    @UiField
    MaterialButton btnBack;
    @UiField
    MaterialButton btnAccept;
    @UiField
    MaterialPanel passwordPanel;

    private NavigationManager navigationManager;
    private SimpleEventHandler registerPatient;
    private String userPassword;

    @Inject
    public ChangeProfileView(NavigationManager navigationManager) {
        setWidget(ourUiBinder.createAndBindUi(this));
        this.navigationManager = navigationManager;
        birthDate.setDateMin(new Date(90, 0, 1));
        birthDate.setDateMax(new Date());
        firstName.addValidator(new BlankValidator<String>("Please, provide your first name!"));
        lastName.addValidator(new BlankValidator<String>("Please, provide your last name!"));
        studyGroup.addValidator(new BlankValidator<String>("Please, provide study group!"));
        email.addValidator(new BlankValidator<String>("Please, provide your email!"));
        repeatNewPassword.addValidator(new BlankValidator<String>("Please, repeat new password!"));
        password.addValidator(new BlankValidator<String>("Please, provide an old password!"));
        newPassword.addValidator(new BlankValidator<String>("Please, provide a new password!"));

    }

    @Override
    public void addAcceptButtonClickHandler (ClickHandler handler){
        btnAccept.addClickHandler(handler);
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
    @UiHandler("yes")
    protected void yesClick(ClickEvent e){
        if (!yes.getValue()){
            yes.setValue(false);
            no.setValue(true);
            passwordPanel.setEnabled(false);
        }
        else{
            yes.setValue(true);
            no.setValue(false);
            passwordPanel.setEnabled(true);
        }
    }
    @UiHandler("no")
    protected void noClick(ClickEvent e){
        if (!no.getValue()){
            no.setValue(false);
            yes.setValue(true);
            passwordPanel.setEnabled(true);
        }
        else{
            no.setValue(true);
            yes.setValue(false);
            passwordPanel.setEnabled(false);
        }
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
    public Gender getGender() {
        return men.getValue() ? Gender.M : Gender.W;
    }

    @Override
    public String getStudyGroup(){
        return studyGroup.getText();
    }

    @Override
    public String getPassword() {
        return userPassword;

    }

    @Override
    public void setFirstName(String firstName){
        this.firstName.setText(firstName);
    }
    @Override
    public void setLastName(String lastName){
        this.lastName.setText(lastName);
    }
    @Override
    public void setBirthDay(Date birthDate){
        this.birthDate.setDate(birthDate);
    }
    @Override
    public void setEmail(String email){
        this.email.setText(email);
    }
    @Override
    public void setStudyGroup(String studyGroup){
        this.studyGroup.setText(studyGroup);
    }
    @Override
    public void setGender(Gender gender){
        if (gender.equals(Gender.M)) {
            men.setValue(true);
            woman.setValue(false);
        }
        else{
            men.setValue(false);
            woman.setValue(true);
        }

    }
    @Override
    public void checkPassword(String password){
        userPassword = password;

    }

    @Override
    public String getEmail(){
        return email.getText();
    }

    @Override
    public void registerPatientHandler(SimpleEventHandler handler){
        registerPatient = handler;
    }
    void Validation(){
        if(!firstName.validate() || !lastName.validate() || !studyGroup.validate() || !email.validate()){
            return;
        }
        if (yes.getValue() && password.getText().equals(userPassword))
        if(!newPassword.getText().equals(repeatNewPassword.getText())){
            return;
        }
        else userPassword = newPassword.getText();
    }
}