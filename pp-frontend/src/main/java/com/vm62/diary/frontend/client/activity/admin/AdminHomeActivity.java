package com.vm62.diary.frontend.client.activity.admin;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.ConfirmDialog;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SelectEventHandler;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.AdminServiceAsync;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.List;

@Singleton
public class AdminHomeActivity implements BaseActivity {

    @ImplementedBy(AdminHomeView.class)
    interface IAdminHomeView extends IsWidget{
        void setUserTable(List<UserDTO> users);
        void setUserBanHandler(SelectEventHandler<UserDTO> handler);
        void setCreateCategoryButtonHandler(ClickHandler clickHandler);
        String getCategoryName();
        String getCategoryColor();
        void addComeBackClickHandler(ClickHandler handler);
        void clearCategoryNameAndColor();
        void setCategoryTable(List<CategoryDTO> categoryTable);
    }

    private IAdminHomeView view;
    private NavigationManager navigationManager;
    private AdminServiceAsync adminServiceAsync;
    private NotificationManager notificationManager;
    private ConfirmDialog confirmDialog;
    private Long currentUserId;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    public AdminHomeActivity(IAdminHomeView view, NavigationManager navigationManager, AdminServiceAsync adminServiceAsync,
                             NotificationManager notificationManager, ConfirmDialog confirmDialog){
        this.view = view;
        this.navigationManager = navigationManager;
        this.adminServiceAsync = adminServiceAsync;
        this.notificationManager = notificationManager;
        this.confirmDialog = confirmDialog;
        addEventHandlers();
    }

    public void addEventHandlers(){
        view.setCreateCategoryButtonHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                adminServiceAsync.createCategory(view.getCategoryName(), view.getCategoryColor(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorCategoryCreationFailed());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        notificationManager.showErrorPopupWithoutDetails(constants.successCategoryCreationSuccessful());
                        view.clearCategoryNameAndColor();
                    }
                });
            }
        });

        view.addComeBackClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_MAIN));
            }
        });

        view.setUserBanHandler(new SelectEventHandler<UserDTO>() {
            @Override
            public void onEvent(UserDTO user) {
                currentUserId = user.getUserId();
                if(user.isRegister()){
                    confirmDialog.showDialog(
                            constants.headerBanUser(),
                            constants.wantToBanUser() + user.getFirstName() + " " + user.getLastName() + "?",
                            null,
                            null,
                            new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    adminServiceAsync.banUser(currentUserId, new AsyncCallback<Void>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            notificationManager.showErrorPopupWithoutDetails(constants.errorUserBanFailed());
                                        }

                                        @Override
                                        public void onSuccess(Void result) {
                                            confirmDialog.hide();
                                            populateUserTable();
                                        }
                                    });
                                }
                            },
                            new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    confirmDialog.hide();
                                }
                            },
                            constants.ban(),
                            constants.buttonBack()
                    );
                } else {
                    confirmDialog.showDialog(
                            constants.headerUnbanUser(),
                            constants.wantToUnbanUser() + user.getFirstName() + " " + user.getLastName() + "?",
                            null,
                            null,
                            new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    adminServiceAsync.unbanUser(currentUserId, new AsyncCallback<Void>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            notificationManager.showErrorPopupWithoutDetails(constants.errorUserUnbanFailed());
                                        }

                                        @Override
                                        public void onSuccess(Void result) {
                                            confirmDialog.hide();
                                            populateUserTable();
                                        }
                                    });
                                }
                            },
                            new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    confirmDialog.hide();
                                }
                            },
                            constants.unban(),
                            constants.buttonBack()
                    );
                }
            }
        });
        populateUserTable();
        populateCategory();

    }

    public void populateUserTable(){
        adminServiceAsync.getUsers(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails(constants.errorCanNotRetrieveUsers());
            }

            @Override
            public void onSuccess(List<UserDTO> result) {
                view.setUserTable(result);
            }
        });
    }

    public void populateCategory(){
        adminServiceAsync.getAllCategories(new AsyncCallback<List<CategoryDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails(constants.errorCanNotRetrieveCategory());
            }

            @Override
            public void onSuccess(List<CategoryDTO> result) {
                view.setCategoryTable(result);
            }
        });
    }


    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}
