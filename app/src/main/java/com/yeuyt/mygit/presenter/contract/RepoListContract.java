package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import java.util.List;

public interface RepoListContract {
    interface View extends BaseView {
        void loadRepoList(List<RepositoryInfo> repositoryInfos);
    }

    interface Presenter extends MvpPresenter<View> {
        void userRepoList(String user, int curPage);
        void staredRepoList(String user, int curPage);
        void watchRepoList(String user, int curPage);
    }
}
