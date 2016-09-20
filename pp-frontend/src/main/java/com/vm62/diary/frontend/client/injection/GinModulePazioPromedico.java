package com.vm62.diary.frontend.client.injection;

import com.vm62.diary.frontend.client.activity.IServiceNavigationController;
import com.vm62.diary.frontend.client.activity.ITopHeaderController;
import com.vm62.diary.frontend.client.activity.MainPanelView;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class GinModulePazioPromedico extends AbstractGinModule {

	@Override
    protected void configure() {
        bind(ITopHeaderController.class).to(MainPanelView.class).in(Singleton.class);
        bind(IServiceNavigationController.class).to(MainPanelView.class).in(Singleton.class);
	}
}
