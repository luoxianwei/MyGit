package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.model.entity.Event;

import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import java.util.List;

public interface NewsContract {
    interface View extends BaseView {
        void loadNewsList(List<Event> news);
    }

    interface Presenter extends MvpPresenter<View> {
        void getNews(int page);
    }
}
