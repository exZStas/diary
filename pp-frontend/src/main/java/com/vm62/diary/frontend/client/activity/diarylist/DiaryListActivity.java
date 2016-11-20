package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;


/**
 * Created by Ира on 17.11.2016.
 */
public class DiaryListActivity implements BaseActivity{
    @ImplementedBy(DiaryListView.class)
    public interface IDiaryListView extends IsWidget {

    }

    private IDiaryListView view;

    @Inject
    DiaryListActivity(IDiaryListView view){
        this.view = view;
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}
