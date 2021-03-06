package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.inject.Inject;

import com.google.inject.Singleton;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.components.SignImageListWidget;

import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.*;



import java.lang.*;
import java.util.Date;
import java.util.List;


/**
 * Created by Ира on 15.12.2016.
 */
@Singleton
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
    @UiField
    protected MaterialButton btnBack;
    @UiField
    protected MaterialDatePicker endDate;
    @UiField
    protected MaterialTimePicker tpEnd;
    @UiField (provided = true)
    protected MaterialListBox typeBox;
    @UiField
    MaterialRow signContainer;
    @UiField
    MaterialTextArea descriptArea;
    @UiField
    MaterialTextBox hourTextBox;
    @UiField
    MaterialTextBox minutesTextBox;
    @UiField
    MaterialButton btnCreate;


    private NavigationManager navigationManager;
    private SimpleEventHandler registerPatient;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    public CreateEventView(NavigationManager navigationManager) {
        typeBox = new MaterialListBox();
        typeBox.setVisibleItemCount(5);
        this.navigationManager = navigationManager;
        removeLangButtons();
        setWidget(ourUiBinder.createAndBindUi(this));
        setCaptionHtml(HeaderTitle.EVENT_PANEL.getText());
        btnBack.getElement().getStyle().setBackgroundColor("#ff8f00");
        endDate.setDateMin(new Date(90,1,0));
        endDate.setDateMax(new Date(118,1,0));
        minutesTextBox.setType(InputType.NUMBER);
        minutesTextBox.setValue("5");
        hourTextBox.setValue("0");
        simple.setValue(true);
        complex.setValue(false);

        endDate.addValidator(new BlankValidator<Date>("Please, provide event's start date!"));
        center();
        //addEventHandlers();
        eventName.addValidator(new BlankValidator<String>("Please, provide event's name!"));
    }

    @UiHandler("btnCreate")
    void onCreateEvent(ClickEvent e) {
        if(!eventName.validate() || tp.getTime().isEmpty()){
            tp.setIconColor("red");
            return;
        }
        else if (getComplexity()&&(!eventName.validate() || !endDate.validate() || tpEnd.getTime().isEmpty()||getEndTime().before(getStartTime()))){
            if (getEndTime().before(getStartTime()))
                endDate.setIconColor("red");
            return;
        }

        this.hide();
        registerPatient.onEvent();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));

    }

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
    public void setCategories(List<CategoryDTO> categoryDTOList){
        for(CategoryDTO categoryDTO : categoryDTOList){
            typeBox.addItem(categoryDTO.getName());
        }

    }

    @Override
    public Category getCategory() {
        return Category.values()[typeBox.getSelectedIndex()];
    }
    @Override
    public String getDescription(){
        return descriptArea.getText();
    }
    @Override
    public Boolean getComplexity(){return complex.getValue();}

    @Override
    public void setToday(Date today) {
        startTime = today;
    }

    @Override
    public Date getStartTime(){
        startTime.setHours(tp.getValue().getHours());
        startTime.setMinutes(tp.getValue().getMinutes());
        startTime.setSeconds(0);
        return startTime;}

    @Override
    public Long getDuration(){
        Long l;
        l = (Long.parseLong(minutesTextBox.getText()) + 60 * Long.parseLong(hourTextBox.getText()))*60000;
        return  l;
    }
    @Override
    public Date getEndTime(){

        if (getComplexity()) {
            endTime.setYear(endDate.getValue().getYear());
            endTime.setMonth(endDate.getValue().getMonth());
            endTime.setDate(endDate.getValue().getDate());
            endTime.setHours(tpEnd.getValue().getHours());
            endTime.setMinutes(tpEnd.getValue().getMinutes());

        }
        else {
            endTime.setTime(startTime.getTime()+getDuration());
        }
        return endTime;}


    @Override
    public void registerPatientHandler(SimpleEventHandler handler){
        registerPatient = handler;
    }

    @Override
    public void changeForm(String name, String description, Category category, String sticker, Boolean complexity, Date startTime, Date endTime, Long duration) {
        btnCreate.setText(constants.buttonEdit());
        setCaptionHtml(HeaderTitle.CHANGE_PANEL.getText());
        eventName.setText(name);
        descriptArea.setText(description);
        typeBox.setValue(category.getCategory());
        if (complexity) {
            simple.setValue(false);
            complex.setValue(true);}
        //else simple.setValue(true);
        this.startTime.setTime(startTime.getTime());
        this.endTime.setTime(endTime.getTime());
        tp.setValue(startTime);
        tpEnd.setValue(endTime);
        endDate.setDate(endTime);

        Long min = (duration / (1000 * 60)) % 60;
        Long hours = (duration / (1000 * 60 * 60)) % 24;
        minutesTextBox.setValue(min.toString());
        hourTextBox.setValue(hours.toString());

    }

    @Override
    public void setSignImageList(SignImageListWidget signImageListWidget){
        signContainer.add(signImageListWidget);
    }
}