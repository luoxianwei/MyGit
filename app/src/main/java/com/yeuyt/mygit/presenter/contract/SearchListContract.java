package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.SearchResult;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import java.util.List;

public interface SearchListContract {
    interface View extends BaseView {
        void loadUsersList(SearchResult<UserEntity> userEntities);
        void loadRepositoriesList(SearchResult<RepositoryInfo> repoEntities);
    }

    interface Presenter extends MvpPresenter<View> {

        void searchUser(String keyword, int curPage);
        void searchRepository(String keyword, int curPage);
    }
}
