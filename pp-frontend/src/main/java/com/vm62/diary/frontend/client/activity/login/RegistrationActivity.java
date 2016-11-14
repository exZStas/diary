package com.vm62.diary.frontend.client.activity.login;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;

@Singleton
public class RegistrationActivity implements BaseActivity{

    @ImplementedBy(RegistrationView.class)
    public interface IRegistrationView extends IsWidget{

    }

    private IRegistrationView view;

    @Inject
    RegistrationActivity(IRegistrationView view){
        this.view = view;
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}
