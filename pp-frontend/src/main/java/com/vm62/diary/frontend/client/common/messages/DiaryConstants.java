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

    String copyright();

    @Key("error.eventWasNotDeleted")
    String errorEventWasNotDeleted();

    @Key("error.eventIsNotAvailable")
    String errorEventIsNotAvailable();

    @Key("success.eventWasDeleted")
    String successEventWasDeleted();

    @Key("success.statusWasEdited")
    String successStatusWasEdited();

    @Key("error.unknownError")
    String errorUnknownError();

    @Key("error.incorrectEmailOrPassword")
    String errorIncorrectEmailOrPassword();

    @Key("error.eventWasCanceled")
    String errorEventWasCanceled();

    @Key("success.eventWasCreated")
    String successEventWasCreated();

    @Key("error.profileWasNotChanged")
    String errorProfileWasNotChanged();

    @Key("error.profileIsNotAvailable")
    String errorProfileIsNotAvailable();

    @Key("success.profileWasChanged")
    String successProfileWasChanged();

    @Key("error.eventsAreNotAvailable")
    String errorEventsAreNotAvailable();

    @Key("error.currentDayHasNoEvents")
    String errorCurrentDayHasNoEvents();

    @Key("error.registrationFailed")
    String errorRegistrationFailed();

    @Key("success.registrationSuccessful")
    String successRegistrationSuccessful();

    @Key("error.incorrectPassword")
    String errorIncorrectPassword();

    @Key("error.newPasswordIsNotEqualToRepeated")
    String errorNewPasswordIsNotEqualToRepeated();

    @Key("error.categoryCreationFailed")
    String errorCategoryCreationFailed();

    @Key("success.categoryCreationSuccessful")
    String successCategoryCreationSuccessful();

    @Key("error.userBanFailed")
    String errorUserBanFailed();

    @Key("error.userUnbanFailed")
    String errorUserUnbanFailed();

    @Key("error.canNotRetrieveUsers")
    String errorCanNotRetrieveUsers();

    @Key("error.canNotUpdateSchedule")
    String errorCanNotUpdateSchedule();

    @Key("success.scheduleUpdate")
    String successScheduleUpdate();

    @Key("placeholder.repeatPassword")
    String placeholderRepeatPassword();

    String registration();

    @Key("user.firstName")
    String userFirstName();

    @Key("user.lastName")
    String userLastName();

    @Key("user.birthDate")
    String userBirthDate();

    @Key("user.male")
    String userMale();

    @Key("user.female")
    String userFemale();

    @Key("user.studyGroup")
    String userStudyGroup();

    @Key("button.registerMe")
    String buttonRegisterMe();

    String wantToChangePassword();

    String yes();

    String no();

    @Key("placeholder.newPassword")
    String placeholderNewPassword();

    @Key("placeholder.repeatNewPassword")
    String placeholderRepeatNewPassword();

    @Key("button.change")
    String buttonChange();

    @Key("user.name")
    String userName();

    String createCategory();

    String enterCategoryName();

    String enterColorOfCategory();

    @Key("button.createCategory")
    String buttonCreateCategory();

    @Key("user.firstNameBig")
    String userFirstNameBig();

    @Key("user.lastNameBig")
    String userLastNameBig();

    @Key("user.studyGroupBig")
    String userStudyGroupBig();

    @Key("user.emailBig")
    String userEmailBig();

    String active();

    String inactive();

    @Key("user.status")
    String userStatus();

    @Key("header.usersTable")
    String headerUsersTable();

    String wantToBanUser();

    String wantToUnbanUser();

    String ban();

    String unban();

    @Key("header.banUser")
    String headerBanUser();

    @Key("header.unbanUser")
    String headerUnbanUser();

    @Key("header.undoneChart")
    String headerUndoneChart();

    @Key("header.doneChart")
    String headerDoneChart();

    @Key("error.noUndoneEvents")
    String errorNoUndoneEvents();

    @Key("error.noDoneEvents")
    String errorNoDoneEvents();

    @Key("error.smallEventTime")
    String errorSmallEventTime();

    @Key("error.canNotRetrieveCategory")
    String errorCanNotRetrieveCategory();
}
