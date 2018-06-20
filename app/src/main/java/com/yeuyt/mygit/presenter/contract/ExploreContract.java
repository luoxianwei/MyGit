package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import java.util.List;

public interface ExploreContract {
    interface View extends BaseView {
        void loadGankData(List<RepositoryInfo> repositoryInfos);
    }
    interface Presenter extends MvpPresenter<View> {
        void getGankRepositories(int page);
    }
}
