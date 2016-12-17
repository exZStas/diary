package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.activity.HeaderTitle;
import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.html.Option;

import java.util.Date;

import static com.vm62.diary.common.constants.Category.education;

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
    @UiField
    protected MaterialRange duration;
    @UiField
    protected MaterialLabel lblRange;
    @UiField
    MaterialButton btnBack;
    @UiField
    MaterialDatePicker endDate;
    @UiField
    MaterialTimePicker tpEnd;
    @UiField
    MaterialListBox typeBox;

    private NavigationManager navigationManager;
    private SimpleEventHandler registerPatient;

    @Inject
    public CreateEventView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        setWidget(ourUiBinder.createAndBindUi(this));
        setCaptionHtml(HeaderTitle.EVENT_PANEL.getText());
        btnBack.getElement().getStyle().setBackgroundColor("#ff8f00");
        endDate.setDateMin(new Date(90,1,0));
        endDate.setDateMax(new Date());

        // Возможно, правильнее так добавлять категории
        typeBox.add(new Option(education.getCategory()));


        center();
        addEventHandlers();
        eventName.addValidator(new BlankValidator<String>("Please, provide event's name!"));

    }

    void addEventHandlers(){}

    @UiHandler("btnCreate")
    void onCreateEvent(ClickEvent e) {
        if(!eventName.validate()){
            return;
        }
        this.hide();
        registerPatient.onEvent();
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
    }

    @UiHandler("duration")
    void onRange(ChangeEvent e) {
        lblRange.setText("Duration: " + String.valueOf(duration.getValue()));
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
    public String getType(){return typeBox.getSelectedItemText();}

    @Override
    public Boolean getComplexity(){return simple.getValue() ? simple.getValue() : complex.getValue();}

    @Override
    public Date getStartTime(){return tp.getValue();}

    @Override
    public int getDuration(){return duration.getValue();}
    @Override
    public Date getEndTime(){return tpEnd.getValue();}

    @Override
    public Date getEndDate(){return endDate.getValue();}

    @Override
    public void registerPatientHandler(SimpleEventHandler handler){
        registerPatient = handler;
    }
}