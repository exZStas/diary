package com.vm62.diary.frontend.client.activity;


import com.vm62.diary.frontend.client.common.messages.MessagesProvider;

public enum HeaderTitle {

    ADMIN_LOGIN(MessagesProvider.text().headerAdminLogin()),
    ADMIN_PANEL(MessagesProvider.text().headerAdminPanel()),
    LOGIN_PANEL(MessagesProvider.text().headerEnterDiary()),
    EVENT_PANEL(MessagesProvider.text().headerCreateEvent()),
    CHANGE_PANEL(MessagesProvider.text().headerEditEvent());

    private String text;

    HeaderTitle(String value) {
        this.text = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String title) {
        this.text = title;
    }
}
