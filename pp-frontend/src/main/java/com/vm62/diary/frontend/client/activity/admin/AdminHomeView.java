package com.vm62.diary.frontend.client.activity.admin;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.events.SelectEventHandler;
import com.vm62.diary.frontend.server.service.dto.CategoryDTO;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.server.service.dto.UserDTO;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.List;

@Singleton
public class AdminHomeView extends Composite implements AdminHomeActivity.IAdminHomeView {

    interface AdminHomeViewUiBinder extends UiBinder<MaterialPanel, AdminHomeView>{}

    private static AdminHomeViewUiBinder uiBinder = GWT.create(AdminHomeViewUiBinder.class);

    @UiField(provided = true)
    protected DataGrid<UserDTO> userTable;

    @UiField(provided = true)
    protected DataGrid<CategoryDTO> categoryTable;

    @UiField
    protected MaterialTextBox categoryName;

    @UiField
    protected MaterialTextBox categoryColor;

    @UiField
    protected MaterialButton createButton;

    @UiField
    protected MaterialButton comeBackButton;

    private static final int MAX_TABLE_SIZE = 100;
    private ListDataProvider<UserDTO> userDTOListDataProvider = new ListDataProvider<UserDTO>();
    private ListDataProvider<CategoryDTO> categoryDTOListDataProvider = new ListDataProvider<CategoryDTO>();
    private SelectEventHandler<UserDTO> userBanHandler;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    public AdminHomeView(){
        userTable = new DataGrid<UserDTO>(MAX_TABLE_SIZE);
        categoryTable = new DataGrid<CategoryDTO>(MAX_TABLE_SIZE);
        userTable.setStyleName("striped responsive-table");
        categoryTable.setStyleName("striped responsive-table");
        initWidget(uiBinder.createAndBindUi(this));
        categoryName.setMaxLength(25);
        categoryColor.setMaxLength(25);
        comeBackButton.getElement().getStyle().setBackgroundColor("#ff8f00");

        initUsersTable();
        initCategoryTable();
        addEventHandlers();
    }

    public void addEventHandlers(){
        userTable.addCellPreviewHandler(new CellPreviewEvent.Handler<UserDTO>() {
            @Override
            public void onCellPreview(CellPreviewEvent<UserDTO> event) {
                if(BrowserEvents.CLICK.equals(event.getNativeEvent().getType())){
                    if(userBanHandler != null){
                        userBanHandler.onEvent(event.getValue());
                    }
                }
            }
        });
    }

    private void initCategoryTable(){
        categoryDTOListDataProvider.addDataDisplay(categoryTable);

        initCategoryNameColumn();
        initCategoryColorTable();
    }

    private void initCategoryNameColumn(){
        TextColumn nameColumn = new TextColumn<CategoryDTO>() {
            @Override
            public String getValue(CategoryDTO category) {
                return category.getName();
            }
        };
        categoryTable.addColumn(nameColumn, "Category name");
        categoryTable.setColumnWidth(nameColumn, "40%");
    }

    private void initCategoryColorTable(){
        TextColumn colorColumn = new TextColumn<CategoryDTO>() {
            @Override
            public String getValue(CategoryDTO category) {
                return category.getColor();
            }
        };

        categoryTable.addColumn(colorColumn, "Category color");
        categoryTable.setColumnWidth(colorColumn, "40%");
    }

    private void initUsersTable(){
        userDTOListDataProvider.addDataDisplay(userTable);

        initFirstNameColumn();
        initLastNameColumn();
        initGroupColumn();
        initStatusColumn();
        initEmailColumn();
    }

    private void initFirstNameColumn(){
        TextColumn firstNameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getFirstName();
            }
        };
        userTable.addColumn(firstNameColumn, constants.userFirstNameBig());
        userTable.setColumnWidth(firstNameColumn, "20%");
    }

    private void initLastNameColumn(){
        TextColumn lastNameColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getLastName();
            }
        };
        userTable.addColumn(lastNameColumn, constants.userLastNameBig());
        userTable.setColumnWidth(lastNameColumn, "20%");
    }

    private void initGroupColumn(){
        TextColumn groupColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getStudyGroup();
            }
        };
        userTable.addColumn(groupColumn, constants.userStudyGroupBig());
        userTable.setColumnWidth(groupColumn, "20%");
    }

    public void initEmailColumn(){
        TextColumn emailColumn = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO user) {
                return user.getEmail();
            }
        };
        userTable.addColumn(emailColumn, constants.userEmailBig());
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
                    sb.appendHtmlConstant("<span style=\"color:green\">" + constants.active() + "</span>");
                } else {
                    sb.appendHtmlConstant("<span color:red>" + constants.inactive() + "</span>");
                }
            }
        };
        userTable.addColumn(statusColumn, constants.userStatus());
        userTable.setColumnWidth(statusColumn, "20%");
    }

    @Override
    public void setUserTable(List<UserDTO> users){
        userDTOListDataProvider.getList().clear();
        userDTOListDataProvider.getList().addAll(users);
        userTable.redraw();
    }

    @Override
    public void setCategoryTable(List<CategoryDTO> categoryTable){
        categoryDTOListDataProvider.getList().clear();
        categoryDTOListDataProvider.getList().addAll(categoryTable);
        this.categoryTable.redraw();
    }

    @Override
    public void setUserBanHandler(SelectEventHandler<UserDTO> handler){
        userBanHandler = handler;
    }

    @Override
    public void addComeBackClickHandler(ClickHandler clickHandler){
        comeBackButton.addClickHandler(clickHandler);
    }

    @Override
    public void setCreateCategoryButtonHandler(ClickHandler clickHandler){
        createButton.addClickHandler(clickHandler);
    }

    @Override
    public String getCategoryName(){
        return categoryName.getText();
    }

    @Override
    public String getCategoryColor(){
        return categoryColor.getText();
    }

    @Override
    public void clearCategoryNameAndColor(){
        categoryName.clear();
        categoryColor.clear();
    }
}
