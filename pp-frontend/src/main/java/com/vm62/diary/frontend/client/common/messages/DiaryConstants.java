package com.vm62.diary.frontend.client.common.messages;

import com.google.gwt.i18n.client.Constants;

/**
 * Created by Ира on 29.12.2016.
 */
public interface DiaryConstants extends Constants {
    @DefaultStringValue("TPU's diary")
    String tpuDiary();

    @DefaultStringValue("Events")
    String events();

    @DefaultStringValue("Chart")
    String charts();

    @DefaultStringValue("Update schedule")
    String updateSchedule();

    @DefaultStringValue("Change profile")
    String changeProfile();

    @DefaultStringValue("Log out")
    String logOut();

    @DefaultStringValue("Please provide your email")
    String enterEmail();

    @DefaultStringValue("Please provide your password")
    String enterPassword();

    @Key("category.education")
    String categoryEducation();

    @Key("category.sport")
    String categorySport();

    @Key("category.entertainment")
    String categoryEntertainment();

    @Key("category.work")
    String categoryWork();

    @Key("category.sleep")
    String categorySleep();

    @Key("category.trip")
    String categoryTrip();

    @Key("category.homework")
    String categoryHomework();

    String[] category();

    @Key("placeholder.category")
    String placeholderCategory();

    @Key("placeholder.password")
    String placeholderPassword();

    @Key("placeholder.nameOfEvent")
    String placeholderNameOfEvent();

    @Key("placeholder.description")
    String placeholderDescription();

    @Key("placeholder.simple")
    String placeholderSimple();

    @Key("placeholder.complex")
    String placeholderComplex();

    @Key("placeholder.startTime")
    String placeholderStartTime();

    @Key("placeholder.hours")
    String placeholderHours();

    @Key("placeholder.minutes")
    String placeholderMinutes();

    @Key("placeholder.dateEnd")
    String placeholderDateEnd();

    @Key("placeholder.endTime")
    String placeholderEndTime();

    String duration();

    String complexity();

    @Key("button.back")
    String buttonBack();

    @Key("button.create")
    String buttonCreate();

    @Key("button.login")
    String buttonLogin();

    @Key("button.edit")
    String buttonEdit();

    @Key("header.adminLogin")
    String headerAdminLogin();

    @Key("header.adminPanel")
    String headerAdminPanel();

    @Key("header.enterDiary")
    String headerEnterDiary();

    @Key("header.createEvent")
    String headerCreateEvent();

    @Key("header.editEvent")
    String headerEditEvent();

    @Key("button.registration")
    String buttonRegistration();
}
