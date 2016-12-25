package com.vm62.diary.frontend.client.common.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.util.tools.shared.Md5Utils;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.common.events.SelectEventHandler;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

public class SignImageListWidget extends Composite implements IsWidget {

    interface SignImageListWidgetUiBinder extends UiBinder<MaterialPanel, SignImageListWidget> {
    }

    private static SignImageListWidgetUiBinder uiBinder = GWT.create(SignImageListWidgetUiBinder.class);

    @UiField
    MaterialRow signsPanel;
    private SelectEventHandler<Signs> selectEventHandler;
    private Image image;

    @Inject
    public SignImageListWidget() {
        setWidget(uiBinder.createAndBindUi(this));

        initComponents();
    }

    public MaterialRow getSignPanel(){
        return this.signsPanel;
    }

    private void initComponents() {
        for (Signs sign : Signs.values()) {
            signsPanel.add(createSignImage(sign));
        }
    }

    private Image createSignImage(final Signs sign) {
        image = new Image(sign.getImage());
        image.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (selectEventHandler != null) {
                    selectEventHandler.onEvent(sign);
                }
            }
        });
        return image;

    }


    public void addSelectHandler(SelectEventHandler<Signs> handler) {
        this.selectEventHandler = handler;

    }

}
