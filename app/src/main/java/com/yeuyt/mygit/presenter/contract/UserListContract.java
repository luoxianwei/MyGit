package com.yeuyt.mygit.presenter.contract;

import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import java.util.List;

public interface UserListContract {

    interface View extends BaseView {
        void loadUsersList(List<UserEntity> userEntities);
    }

    interface Presenter extends MvpPresenter<View> {

        void followingUserList(String user, int curPage);
        void followersUserList(String user, int curPage);
    }
}
