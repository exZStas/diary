package com.vm62.diary.frontend.client.injection;


import com.vm62.diary.frontend.client.activity.MainPanelActivity;
import com.vm62.diary.frontend.client.activity.MainPanelView;
import com.vm62.diary.frontend.client.activity.admin.AdminLoginDialog;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;



/**
 * It is main GIN injector that attaches GIN modules and contains factory methods 
 * for 'root' components instantiation.
 *
 */
@GinModules(GinModulePazioPromedico.class)
public interface InjectorPazioPromedico extends Ginjector {

    InjectorPazioPromedico INSTANCE = GWT.create(InjectorPazioPromedico.class);

    AdminLoginDialog getAdminLoginDialog();

    NotificationManager getNotificationManager();
    NavigationManager getNavigationManager();

    MainPanelView getMainPanel();
    MainPanelActivity getMainPanelActivity();


    Provider<MainPanelActivity> getMainPanelActivityProvide();

}