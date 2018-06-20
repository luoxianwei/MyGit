package com.yeuyt.mygit.presenter.contract;


import com.yeuyt.mygit.presenter.MvpPresenter;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void showOnSucess();
    }
    interface Presenter extends MvpPresenter<View> {
        void login(String name, String password);
        String getLoginName();
    }
}
