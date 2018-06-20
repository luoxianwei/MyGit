package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.ui.interfaceView.BaseView;

public interface MvpPresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
