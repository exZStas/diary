package com.vm62.diary.frontend.client.activity;

public enum HeaderTitle {

    ADMIN_LOGIN("Admin login"),
    ADMIN_PANEL("Admin panel"),
    LOGIN_PANEL("Enter to your personal diary"),
    EVENT_PANEL("Create new Event");

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
