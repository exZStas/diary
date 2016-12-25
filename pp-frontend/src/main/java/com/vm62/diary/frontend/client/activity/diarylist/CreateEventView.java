package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.inject.Inject;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.common.constants.Sticker;
import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.components.SignImageListWidget;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.base.validator.Validator;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.html.Option;


import java.lang.*;
import java.util.Date;
import java.util.List;

import static com.vm62.diary.common.constants.Category.education;
import com.google.gwt.user.client.ui.Image;

import javax.validation.constraints.Null;

/**
 * Created by Ира on 15.12.2016.
 */

public class CreateEventView extends CDialogBox implements CreateEventActivity.ICreateEventView{
    private static CreateEventViewUiBinder ourUiBinder = GWT.create(CreateEventViewUiBinder.class);
    interface CreateEventViewUiBinder extends UiBinder<MaterialRow, CreateEventView> {
    }

    @UiField
    protected MaterialTextBox eventName;
    @UiField
    protected MaterialCheckBox simple;
    @UiField
    protected MaterialCheckBox complex;
    @UiField
    protected MaterialTimePicker tp;
//    @UiField
//    protected MaterialRange duration;
//    @UiField
//    protected MaterialLabel lblRange;
    @UiField
    protected MaterialButton btnBack;
    @UiField
    protected MaterialDatePicker endDate;
    @UiField
    protected MaterialTimePicker tpEnd;
    @UiField
    protected MaterialListBox typeBox;
    @UiField
    protected MaterialChip impChip;
    @UiField
    MaterialRow signContainer;
    @UiField
    MaterialTextArea descriptArea;
    @UiField
    MaterialTextBox hourTextBox;
    @UiField
    MaterialTextBox minutesTextBox;


    private NavigationManager navigationManager;
    private SimpleEventHandler registerPatient;
    private Date startTime;
    private Date endTime;

    @Inject
    public CreateEventView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        setWidget(ourUiBinder.createAndBindUi(this));
        setCaptionHtml(HeaderTitle.EVENT_PANEL.getText());
        btnBack.getElement().getStyle().setBackgroundColor("#ff8f00");
        endDate.setDateMin(new Date(90,1,0));
        endDate.setDateMax(new Date());
        minutesTextBox.setType(InputType.NUMBER);
        minutesTextBox.setValue("5");
        hourTextBox.setValue("0");
        impChip.setUrl("/res/imp.png");
        simple.setValue(true);
        complex.setValue(false);

        // Возможно, правильнее так добавлять категории
        typeBox.add(new Option(education.getCategory()));
        endDate.addValidator(new BlankValidator<Date>("Please, provide event's start date!"));

        center();
        addEventHandlers();
        eventName.addValidator(new BlankValidator<String>("Please, provide event's name!"));



    }

    void addEventHandlers(){

    }

    @UiHandler("btnCreate")
    void onCreateEvent(ClickEvent e) {
        if(!eventName.validate() || tp.getTime().isEmpty()){
            tp.setIconColor("red");
            return;
        }
        else if (getComplexity()&&(!eventName.validate() || tp.getTime().isEmpty() || tpEnd.getTime().isEmpty())){
            return;
        }

        this.hide();
        registerPatient.onEvent();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));

    }

//    @UiHandler("duration")
//    void onRange(ChangeEvent e) {
//        lblRange.setText("Duration: " + String.valueOf(duration.getValue()));
//    }
    @UiHandler("btnBack")
    protected void onBackEvent(ClickEvent e){
        this.hide();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
    }

    @UiHandler("simple")
    protected void simpleClick(ClickEvent event){
        if (!simple.getValue()){
            simple.setValue(false);
            complex.setValue(true);
            tpEnd.setEnabled(true);
            endDate.setEnabled(true);
        } else {
            simple.setValue(true);
            complex.setValue(false);
            tpEnd.setEnabled(false);
            endDate.setEnabled(false);
            tpEnd.clear();
            endDate.clear();
        }
    }

    @UiHandler("complex")
    protected void complexClick(ClickEvent event){
        if (!complex.getValue()){
            complex.setValue(false);
            simple.setValue(true);
            tpEnd.setEnabled(false);
            endDate.setEnabled(false);
            tpEnd.clear();
            endDate.clear();
        } else {
            complex.setValue(true);
            simple.setValue(false);
            tpEnd.setEnabled(true);
            endDate.setEnabled(true);
        }
    }


    @Override
    public String getName(){ return eventName.getText();}

    @Override
    public Category getCategory() {

        return Category.valueOf(typeBox.getSelectedItemText().toLowerCase());
    }
    @Override
    public String getDescription(){
        return descriptArea.getText();
    }
    @Override
    public Boolean getComplexity(){return complex.getValue();}

    @Override
    public Date getStartTime(){
        startTime = new Date();
        startTime.setHours(tp.getValue().getHours());
        startTime.setMinutes(tp.getValue().getMinutes());
        return startTime;}

    @Override
    public Long getDuration(){
        Long l;
        l = (Long.parseLong(minutesTextBox.getText()) + 60 * Long.parseLong(hourTextBox.getText()))*60000;
        return  l;
    }
    @Override
    public Date getEndTime(){

        endTime = new Date();
        if (getComplexity()) {
            endTime.setYear(endDate.getValue().getYear());
            endTime.setMonth(endDate.getValue().getMonth());
            endTime.setDate(endDate.getValue().getDay());
            endTime.setHours(tpEnd.getValue().getHours());
            endTime.setMinutes(tpEnd.getValue().getMinutes());
        }
        else {
            endTime = startTime;
            endTime.setTime(endTime.getTime()+getDuration());
        }
        return endTime;}


    @Override
    public void registerPatientHandler(SimpleEventHandler handler){
        registerPatient = handler;
    }

    @Override
    public void setSignImageList(SignImageListWidget signImageListWidget){
        signContainer.add(signImageListWidget);
    }
}