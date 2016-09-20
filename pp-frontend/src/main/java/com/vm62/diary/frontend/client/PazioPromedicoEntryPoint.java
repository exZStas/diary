package com.vm62.diary.frontend.client;

import com.vm62.diary.frontend.client.activity.MainPanelActivity;
import com.vm62.diary.frontend.client.activity.MainPanelView;
import com.vm62.diary.frontend.client.activity.admin.AdminLoginDialog;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.injection.InjectorPazioPromedico;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.material.design.client.MaterialDesign;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class PazioPromedicoEntryPoint extends MaterialDesign {

    public static final InjectorPazioPromedico injector = InjectorPazioPromedico.INSTANCE;
    private NavigationManager navigationManager;
    private AdminLoginDialog adminLoginDialog;

    private boolean ctrl = false;
    private boolean shift = false;

    public void onModuleLoad() {
        setKeyboardNavigationHandler();
        ScriptInjector.fromUrl("https://code.jquery.com/jquery-3.1.0.min.js");
        adminLoginDialog = injector.getAdminLoginDialog();
        navigationManager = injector.getNavigationManager();

        MainPanelView mainPanel = injector.getMainPanel();

        navigationManager = injector.getNavigationManager();
        RootPanel bodyPanel = RootPanel.get();
        navigationManager.init(bodyPanel, mainPanel.getMainContentPanel());

        MainPanelActivity mainPanelActivity = injector.getMainPanelActivity();
        mainPanelActivity.start(bodyPanel, null);
    }


    private void setKeyboardNavigationHandler() {
        Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                NativeEvent ne = event.getNativeEvent();

                // handle only keyboard events (if event not canceled)
                if (ne.getKeyCode() > 0 && !event.isCanceled()) {
                    /**
                     * Prevent user from using keyboard for page navigation (Backspace).
                     */
                    EventTarget target = ne.getEventTarget();
                    // check if event was BACKSPACE pressed
                    if (target != null && ne.getKeyCode() == KeyCodes.KEY_BACKSPACE) {
                        // checks if current receiver of event is not textbox/INPUT element
                        Element el = Element.as(target);
                        boolean cancelBackspace = true;
                        String tagName = el.getTagName().toUpperCase();
                        String className = el.getClassName();
                        if(className.equals("note-editable")){
                            tagName = "TEXTAREA";
                        }
                        switch (tagName) {
                            case "INPUT":
                                InputElement inputElement = InputElement.as(el);
                                cancelBackspace = inputElement.isReadOnly()
                                        || inputElement.isDisabled()
                                        || !inputElement.getType().matches("(?i)text|password|search");
                                break;
                            case "TEXTAREA":
                                TextAreaElement textAreaElement = TextAreaElement.as(el);
                                cancelBackspace = textAreaElement.isReadOnly() || textAreaElement.isDisabled();
                                break;
                        }
                        if (cancelBackspace) {
                            cancelEvent(event);
                        }
                    }

                }

                if ("keydown".equals(ne.getType())) {
                    if (ne.getKeyCode() == KeyCodes.KEY_CTRL){
                        ctrl = true;
                    }
                    if (ne.getKeyCode() == KeyCodes.KEY_SHIFT) {
                        shift = true;
                    }
                    if (ne.getKeyCode() == KeyCodes.KEY_HOME && ctrl && shift) {
                        adminLoginDialog.showDialog();
                        ctrl = false;
                        shift = false;
                    }
                }

                if ("keyup".equals(ne.getType())) {
                    if (ne.getKeyCode() == KeyCodes.KEY_CTRL) {
                        ctrl = false;
                    }
                    if (ne.getKeyCode() == KeyCodes.KEY_SHIFT){
                        shift = false;
                    }
                }
            }
        });
    }


    /**
     * Cancels browser input event
     */
    private void cancelEvent(Event.NativePreviewEvent event) {
        NativeEvent ne = event.getNativeEvent();
        ne.preventDefault();
        ne.stopPropagation();
        event.cancel();
    }

}
