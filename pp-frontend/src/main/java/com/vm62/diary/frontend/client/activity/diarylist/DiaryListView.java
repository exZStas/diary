package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gwt.material.design.client.ui.*;

@Singleton
public class DiaryListView extends Composite implements DiaryListActivity.IDiaryListView {
    private static DiaryListUiBinder uiBinder = GWT.create(DiaryListUiBinder.class);
    interface DiaryListUiBinder extends UiBinder<Widget, DiaryListView> {

    }

    @UiField
    MaterialNavBar navBar;


    @Inject
    public DiaryListView() {
                setWidget(uiBinder.createAndBindUi(this));
            }


}
