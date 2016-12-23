package com.vm62.diary.frontend.client.common.dialogs;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Utility class for displaying notification messages.
 */
@Singleton
public class NotificationManager extends Composite{

    @Inject
    public NotificationManager() {

    }

    /**
     * Displays info popup with a given message.
     *
      * @param message
     */
    public void showInfoPopup(String message) {
        MaterialToast toast = new MaterialToast();
        toast.toast(message, "green");
    }

    /**
     * Notifies an user about not fatal error (despite of the error, it's possible to continue)
     * without link to details.
     *
     * @param message
     */
    public void showErrorPopupWithoutDetails(String message, Boolean error) {
        MaterialToast toast = new MaterialToast();
        if (error) toast.toast(message, "blue");
        else toast.toast(message, "red");
    }


    /**
     * Notifies an user about not fatal error (despite of the error, it's possible to continue).
     *.
     * @param message
     * @param cause [optional]
     */

}
