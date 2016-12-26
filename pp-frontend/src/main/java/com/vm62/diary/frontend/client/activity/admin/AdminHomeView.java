package com.vm62.diary.frontend.client.activity.admin;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import gwt.material.design.client.ui.MaterialPanel;

import java.util.List;

@Singleton
public class AdminHomeView extends Composite implements AdminHomeActivity.IAdminHomeView {

    interface AdminHomeViewUiBinder extends UiBinder<MaterialPanel, AdminHomeView>{}

    private static AdminHomeViewUiBinder uiBinder = GWT.create(AdminHomeViewUiBinder.class);

    @UiField(provided = true)
    protected DataGrid<UserDTO> userTable;

    private static final int MAX_TABLE_SIZE = 100;
    private ListDataProvider<UserDTO> userDTOListDataProvider = new ListDataProvider<UserDTO>();

    @Inject
    public AdminHomeView(){
        userTable = new DataGrid<UserDTO>(MAX_TABLE_SIZE);
        userTable.setStyleName("striped responsive-table");
        initWidget(uiBinder.createAndBindUi(this));

        initTable();
    }

    private void initTable(){
        userDTOListDataProvider.addDataDisplay(userTable);

        initFirstNameColumn();
        initLastNameColumn();
        initGroupColumn();
        initEmailColumn();
        initStatusColumn();
    }

    private void initFirstNameColumn(){
        TextColumn firstNameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getFirstName();
            }
        };
        userTable.addColumn(firstNameColumn, "First name");
        userTable.setColumnWidth(firstNameColumn, "20%");
    }

    private void initLastNameColumn(){
        TextColumn lastNameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getLastName();
            }
        };
        userTable.addColumn(lastNameColumn, "Last name");
        userTable.setColumnWidth(lastNameColumn, "20%");
    }

    private void initGroupColumn(){
        TextColumn groupColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getStudyGroup();
            }
        };
        userTable.addColumn(groupColumn, "Study group");
        userTable.setColumnWidth(groupColumn, "20%");
    }

    public void initEmailColumn(){
        TextColumn emailColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getEmail();
            }
        };
        userTable.addColumn(emailColumn, "Email");
        userTable.setColumnWidth(emailColumn, "20%");
    }

    public void initStatusColumn(){
        TextColumn statusColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {

                return null;
            }

            @Override
            public void render(Cell.Context context, UserDTO user, SafeHtmlBuilder sb) {
                if(user.isRegister()){
                    sb.appendHtmlConstant("<span style=\"color:green\">ACTIVE</span>");
                } else {
                    sb.appendHtmlConstant("<span color:red>INACTIVE</span>");
                }
            }
        };
        userTable.addColumn(statusColumn, "Status");
        userTable.setColumnWidth(statusColumn, "20%");
    }

    @Override
    public void setUserTable(List<UserDTO> users){
        userDTOListDataProvider.getList().clear();
        userDTOListDataProvider.getList().addAll(users);
        userTable.redraw();
    }
}
