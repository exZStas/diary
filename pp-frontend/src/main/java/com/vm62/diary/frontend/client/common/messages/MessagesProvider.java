package com.vm62.diary.frontend.client.common.messages;


import com.google.gwt.core.client.GWT;

public class MessagesProvider {

    private static CommonMessages LABEL = GWT.create(CommonMessages.class);
    private static ErrorMessages ERROR = GWT.create(ErrorMessages.class);
    private static DiaryConstants CONSTANT = GWT.create(DiaryConstants.class);

    public static CommonMessages label() {
        return LABEL;
    }
    public static ErrorMessages error() {
        return ERROR;
    }
    public static DiaryConstants text() {return CONSTANT;}
}