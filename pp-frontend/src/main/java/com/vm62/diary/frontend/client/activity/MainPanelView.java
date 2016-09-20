package com.vm62.diary.frontend.client.activity;

import com.vm62.diary.frontend.client.common.navigation.NavigationManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;

@Singleton
public class MainPanelView extends Composite implements MainPanelActivity.IMainPanelView, ITopHeaderController, IServiceNavigationController {
    private static MainPanelView.MainPanelViewUiBinder uiBinder = GWT.create(MainPanelView.MainPanelViewUiBinder.class);

    interface MainPanelViewUiBinder extends UiBinder<HTMLPanel, MainPanelView> {

    }

    @UiField
    protected MaterialLink titleLabel;

    @UiField
    protected HTMLPanel mainPanel;

    @UiField
    protected MaterialPanel contentPanel;
    @UiField
    protected MaterialLink breadcrumb;
    @UiField
    protected MaterialLabel copyrightLabel;
    @UiField
    protected MaterialLabel helloLabel;


    private NavigationManager navigationManager;

    @Inject
    public MainPanelView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;

        initWidget(uiBinder.createAndBindUi(this));

        addEventListeners();
    }

    public void addEventListeners() {
        //for event listeners
    }

    @Override
    public HasWidgets getMainContentPanel() {
        return contentPanel;
    }

    @Override
    public void addHeaderTitleClickHandler(ClickHandler handler) {
        titleLabel.addClickHandler(handler);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void setHeaderTitle(String title) {
        titleLabel.setText(title);
    }

    @Override
    public void setIcon(IconType iconType) {
        titleLabel.setIconType(iconType);
    }

    @Override
    public void setBreadcrumb(String label, ClickHandler clickHandler) {
        breadcrumb.setText(label);
        breadcrumb.addClickHandler(clickHandler);
        //override default breadcrumb style for correct view
        breadcrumb.setLayoutPosition(Style.Position.RELATIVE);
        breadcrumb.setHeight("auto");
        breadcrumb.setWidth("auto");
        breadcrumb.setOverflow(Style.Overflow.HIDDEN);


    }

    @Override
    public void setBreadcrumbVisible(boolean visible) {
        breadcrumb.setVisible(visible);
    }



    @Override
    public void navigateByServiceURL() {

    }

}
