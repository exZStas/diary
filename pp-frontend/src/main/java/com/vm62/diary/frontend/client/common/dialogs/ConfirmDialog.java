package com.vm62.diary.frontend.client.common.dialogs;

import com.vm62.diary.frontend.client.common.components.CDialogBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;

public class ConfirmDialog extends CDialogBox {

    private static ConfirmDialogUiBinder ourUiBinder = GWT.create(ConfirmDialogUiBinder.class);
    interface ConfirmDialogUiBinder extends UiBinder<MaterialPanel, ConfirmDialog> {}

    @UiField
    protected MaterialLabel topLabel;
    @UiField
    protected MaterialLabel middleLabel;
    @UiField
    protected MaterialLabel bottomLabel;
    @UiField
    protected MaterialButton closeButton;
    @UiField
    protected MaterialButton yesButton;

    public ConfirmDialog() {
        setWidget(ourUiBinder.createAndBindUi(this));
        setHeight("23em");

        addEventListeners();
    }

    private void addEventListeners() {
        ClickHandler commonClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hideIfVisible();
            }
        };
        closeButton.addClickHandler(commonClickHandler);
    }

    public void closeConfirmDialog() {
        this.hide();
    }

    public void showDialog(String header, String message, String message2, String message3, ClickHandler yesButtonClickHandler, ClickHandler closeButtonClickHandler, String textForYesButton, String textForNoButton) {

        setCaptionHtml(header);

        topLabel.setText(message);
        middleLabel.setText(message2);
        bottomLabel.setText(message3);

        center();

        closeButton.setTabIndex(1);
        yesButton.setTabIndex(1);

        if (yesButtonClickHandler == null){
            yesButton.clear();
            yesButton.removeFromParent();
        }

        if (closeButtonClickHandler == null){
            closeButton.clear();
            closeButton.removeFromParent();

        }

        if (yesButtonClickHandler != null) {
            yesButton.setText(textForYesButton);
            yesButton.addClickHandler(yesButtonClickHandler);
        }

        if (closeButtonClickHandler != null) {
            closeButton.setText(textForNoButton);
            closeButton.addClickHandler(closeButtonClickHandler);
        }
    }

}
