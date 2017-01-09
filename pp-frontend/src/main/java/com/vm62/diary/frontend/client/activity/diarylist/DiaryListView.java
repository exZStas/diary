package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.charts.client.*;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.event.ReadyEvent;
import com.googlecode.gwt.charts.client.event.ReadyHandler;
import com.googlecode.gwt.charts.client.options.TextStyle;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.components.Images;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.Selection;

import java.util.*;


@Singleton
public class DiaryListView extends Composite implements DiaryListActivity.IDiaryListView {
    private static DiaryListUiBinder uiBinder = GWT.create(DiaryListUiBinder.class);

    interface DiaryListUiBinder extends UiBinder<HTMLPanel, DiaryListView> {

    }

    @UiField
    protected MaterialNavBar navBar;
    @UiField
    protected MaterialButton btnAddEvent;
    @UiField
    protected HTMLPanel diaryPanel;
    @UiField
    HTMLPanel scheduleList;
    @UiField
    MaterialLink logOutBtn;
    @UiField
    MaterialLabel userNameLabel;
    @UiField
    MaterialImage userImage;
    @UiField
    MaterialSideProfile userProfile;
    @UiField
    MaterialButton btnScrollLeft;
    @UiField
    MaterialButton btnScrollRight;
    @UiField
    MaterialLink eventLink;
    @UiField
    MaterialLink scheduleUpdateLink;
    @UiField
    MaterialLink chartLink;
    @UiField
    MaterialLink changeProfileBtn;
    @UiField
    MaterialRow container;
    @UiField
    MaterialLabel todayLabel;
    @UiField
    MaterialLabel footerCopyright;
    @UiField
    HTMLPanel unDonePanel;
    @UiField
    HTMLPanel donePanel;

    private NavigationManager navigationManager;
    private EventServiceAsync eventServiceAsync;

    private List<EventView> eventViewList = new ArrayList<>();
    private List<EventDTO> eventDTOs = new ArrayList<>();
    private NotificationManager notificationManager;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    public static Gender userGender;
    public static String userName;
    public Date today = new Date();
    private Map<String,Long> pies = new HashMap<String, Long>();
    private MaterialColumn col = new MaterialColumn();
    private String pieTitle = new String();
    private Map<String, Long> unDoneEvents = new HashMap<String, Long>();
    private Map<String, Long> doneEvents = new HashMap<String, Long>();


    @Inject
    public DiaryListView(NavigationManager navigationManager, EventServiceAsync eventServiceAsync, NotificationManager notificationManager) {
        //currentTime.setText(today.toString());
        //navBar.add(currentTime);
        this.notificationManager = notificationManager;
        this.navigationManager = navigationManager;
        this.eventServiceAsync = eventServiceAsync;
        setWidget(uiBinder.createAndBindUi(this));
        userProfile.setUrl(Images.USER_BG.getImage());
//        scheduleList.sinkEvents(Event.ONCLICK);
        footerCopyright.setText("© " + constants.copyright());

        scheduleList.getElement().getStyle().setProperty("height", "calc(100% - 150px)");
        scheduleList.addStyleName("schedule-list");
        btnScrollLeft.addStyleName("scroll-btn left");
        btnScrollRight.addStyleName("scroll-btn right");
        btnScrollLeft.setIconType(IconType.KEYBOARD_ARROW_LEFT);
        btnScrollRight.setIconType(IconType.KEYBOARD_ARROW_RIGHT);

        getEventDTOs();
    }


    public void getEventDTOs(){
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        eventServiceAsync.getEventsByDayForUser(today, new AsyncCallback<List<EventDTO>>(){
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<EventDTO> result) {
                setSchedule(result);
            }
        });
    }
    @UiHandler("btnAddEvent")
    void onOpenCreateEventWindow(ClickEvent e) {
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CREATE_EVENT_ACTIVITY));
    }

    @UiHandler("changeProfileBtn")
    void onOpenChangeForm(ClickEvent e){
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CHANGE_PROFILE_ACTIVITY));
    }
    @Override
    public void addChartButtonClickHandler (ClickHandler handler){
        chartLink.addClickHandler(handler);
    }

    @Override
    public void setDiaryList() {
        scheduleList.getElement().getStyle().setProperty("height", "calc(100% - 150px)");
        scheduleList.addStyleName("schedule-list");
        btnScrollLeft.addStyleName("scroll-btn left");
        btnScrollRight.addStyleName("scroll-btn right");
        btnScrollLeft.setIconType(IconType.KEYBOARD_ARROW_LEFT);
        btnScrollRight.setIconType(IconType.KEYBOARD_ARROW_RIGHT);

        getEventDTOs();

        for(EventDTO eventDTO : eventDTOs){
            scheduleList.add(new EventView(eventDTO, navigationManager, eventServiceAsync, notificationManager ));
        }
    }

    @Override
    public void setChartParameters() {
        setUserName(userName);
        setUserPicture(userGender);
        container.setWidth("80%");
        container.setHeight("85%");
        container.setFloat(Style.Float.RIGHT);
        scheduleList.removeFromParent();
    }

    @Override
    public void setDayOfList(Date today) {
        DateTimeFormat df = DateTimeFormat.getMediumDateFormat();
        todayLabel.setText(df.format(today));
    }

    @Override
    public void updateSchedule(ClickHandler handler) {
        scheduleUpdateLink.addClickHandler(handler);
    }

    @Override
    public void buttonScrollRightClick(ClickHandler handler) {
        scheduleList.clear();
        btnScrollRight.addClickHandler(handler);
    }
    @Override
    public void buttonScrollLeftClick(ClickHandler handler) {
        scheduleList.clear();
        btnScrollLeft.addClickHandler(handler);
    }

    @Override
    public Date getToday() {
        return today;
    }
    private String[] colors;

    @Override
    public void createPieCharts( Map<String,Long> unDone, Map<String,Long> done){
        unDoneEvents = unDone;
        doneEvents =done;
        pies = unDoneEvents;
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);

        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                if (!pies.isEmpty()) {
                    PieChart chart = new PieChart();
                    chart.setWidth("100%");
                    chart.setHeight("100%");
                    unDonePanel.add(chart);
                    DataTable dataTable = getDataTableForChart();

                    PieOpt opt = new PieOpt();
                    opt.setColors(colors);
                    opt.setTitle(constants.headerUndoneChart());
                    chart.draw(dataTable, opt.get());
                }
                else notificationManager.showErrorPopupWithoutDetails(constants.errorNoUndoneEvents());

                pies = doneEvents;
                if (!pies.isEmpty()) {
                    PieChart chart2 = new PieChart();
                    chart2.setWidth("100%");
                    chart2.setHeight("100%");
                    donePanel.add(chart2);
                    DataTable dataTable2 = getDataTableForChart();

                    PieOpt opt2 = new PieOpt();
                    opt2.setColors(colors);
                    opt2.setTitle(constants.headerDoneChart());
                    chart2.draw(dataTable2, opt2.get());
                }
                else notificationManager.showErrorPopupWithoutDetails(constants.errorNoDoneEvents());
            }
        });
    }

    private DataTable getDataTableForChart(){
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Category");
        dataTable.addColumn(ColumnType.NUMBER, "Minutes per Day");
        dataTable.addRows(pies.size());
        colors = new String[pies.size()];
        Integer i = 0, j = 0;
        for (Map.Entry entry : pies.entrySet()) {
            //получить ключ
            dataTable.setValue(i, j, entry.getKey().toString());
            //получить значение
            dataTable.setValue(i, j + 1, Integer.parseInt(entry.getValue().toString()));
            colors[i] = Category.valueOf(entry.getKey().toString().toLowerCase()).getColor();
            i++;
            j = 0;
        }
        return dataTable;

    }
    @UiHandler("logOutBtn")
    void onClickLogOutBtn(ClickEvent e){
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_MAIN));
    }

    @UiHandler("btnScrollLeft")
    void onLeftScroll(ClickEvent e) {

    }

    @UiHandler("btnScrollRight")
    void onRightScroll(ClickEvent e) {

    }

    @Override
    public void setSchedule(List<EventDTO> events){
        for (int i = 0; i < eventViewList.size(); i++) {
            scheduleList.remove(eventViewList.get(i));
        }
        eventViewList.clear();

        for (int i = 0; i < events.size(); i++) {
            EventView event = new EventView(events.get(i),navigationManager, eventServiceAsync, notificationManager);
            scheduleList.add(event);
            eventViewList.add(event);
        }
        return;
    }

    public void setNewEvent(EventDTO event) {
        EventView eventView = new EventView(event, navigationManager, eventServiceAsync, notificationManager);
        scheduleList.add(eventView);
        eventViewList.add(eventView);
        return;
    }

    @Override
    public void setUserName(String name){
        userNameLabel.setText(name);
        userName = name;
    }
    @Override
    public void setUserPicture(Gender gender){
        if (gender.equals(Gender.M))
            userImage.setUrl(Images.MAN.getImage());
        else
            userImage.setUrl(Images.WOMAN.getImage());
        userGender = gender;
    }

    class PieOpt {
        private PieChartOptions _this;

        private PieOpt() {
            _this = PieChartOptions.create();
        }

        public PieChartOptions get() {
            return _this;
        }

        public final void setColors(String... colors) {
            _this.setColors(colors);
        }
        public final void setIs3D(boolean is3D) {
            _this.setIs3D(is3D);
        }

        public final void setTitle(String title) {
            _this.setTitle(title);
        }

        public final void setTitleTextStyle(TextStyle textStyle) {
            _this.setTitleTextStyle(textStyle);
        }
    }


}
