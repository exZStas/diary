package com.vm62.diary.frontend.client.common.messages;

import com.google.gwt.i18n.client.Messages;

import java.util.Date;

public interface CommonMessages extends Messages {
    @DefaultMessage("Schedule was updated to {0,date,medium}")
    String updatedTo(Date endDay);
}

