package com.vm62.diary.frontend.client.common.events;

import com.google.gwt.event.shared.EventHandler;

public interface SelectEventHandler<T> extends EventHandler {
    void onEvent(T selectedObject);
}
