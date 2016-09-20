package com.vm62.diary.frontend.client.injection;

import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

/**
 * Asynchronous proxy for BaseActivity. It contains logic of lazy loading that based on using gin AsyncProvider.
 *
 * @param <T>
 */
public class ActivityAsyncProxy<T extends BaseActivity> implements BaseActivity {

    @Inject
    private AsyncProvider<T> provider;

    private BaseActivity impl;

    @Override
    public void start(final HasWidgets display, final NavigationPlace place) {
        provider.get(new AsyncCallback<T>() {

            @Override
            public void onSuccess(T result) {
	            impl = result;
	            impl.start(display, place);
            }

            @Override
            public void onFailure(Throwable caught) {
                // NotificationManager is not used here, because we don't know
                // is any components loaded?
                Window.alert(caught.getMessage());
            }
        });
    }

    @Override
    public void stop() {
        impl.stop();
    }
}
