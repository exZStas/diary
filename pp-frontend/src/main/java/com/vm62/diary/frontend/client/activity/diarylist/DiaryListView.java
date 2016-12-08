package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.*;
import com.google.gwt.user.client.ui.Label;

import java.util.Date;

@Singleton
public class DiaryListView extends Composite implements DiaryListActivity.IDiaryListView {
    private static DiaryListUiBinder uiBinder = GWT.create(DiaryListUiBinder.class);
    interface DiaryListUiBinder extends UiBinder<Widget, DiaryListView> {

    }

    @UiField
    protected MaterialNavBar navBar;
    @UiField
    protected MaterialButton btnAddEvent;
    @UiField
    MaterialWindow window;
    @UiField
    protected MaterialTextBox eventName;
    @UiField
    protected MaterialCheckBox simple, complex;
    @UiField
    protected MaterialTimePicker tp;
    @UiField
    protected MaterialButton btnCreate;
    @UiField
    protected MaterialRange duration;
    @UiField
    MaterialLabel lblRange;

    private Label currentTime = new Label();
    Date today = new Date();


    @Inject
    public DiaryListView() {
        //currentTime.setText(today.toString());
        //navBar.add(currentTime);
        setWidget(uiBinder.createAndBindUi(this));


    }
    @UiHandler("btnAddEvent")
    void onOpenWindow(ClickEvent e) {

        window.setMaximize(false);
        window.openWindow();
    }
    @UiHandler("btnCreate")
    void onCreateEvent(ClickEvent e) {
        window.closeWindow();
    }

    @UiHandler("duration")
    void onRange(ChangeEvent e) {
        lblRange.setText("Value: " + String.valueOf(duration.getValue()));
    }



}
