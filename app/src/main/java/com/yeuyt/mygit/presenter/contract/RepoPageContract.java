package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

public interface RepoPageContract {
    interface View extends BaseView {
        void loadReadMe(String url);
    }

    interface Presenter extends MvpPresenter<View> {
        void getReadMe(String owner, String repoName);
    }
}
