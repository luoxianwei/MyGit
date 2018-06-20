package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.di.GitApplication;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.interfaceView.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends BaseView> implements MvpPresenter<T> {
    private CompositeDisposable disposables;
    private T view;
    @Override
    public void attachView(T view) {
        this.view = view;
        disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        view = null;
        if (disposables != null) {
            disposables.dispose();
            disposables = null;
        }
    }

    public T getView() {
        return view;
    }

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public boolean checkLogin() {
        if (AccountHelper.isLogin(GitApplication.getContext()))
            return true;
        Utils.showToastLong("请登陆");
        return false;
    }

    public boolean checkNetwork() {
        if (!Utils.isNetworkAvailable(GitApplication.getContext())) {
            Utils.showToastLong("请检查网络");
            return false;
        }
        return true;
    }

}
