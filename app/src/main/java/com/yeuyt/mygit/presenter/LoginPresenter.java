package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.di.GitApplication;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.contract.LoginContract;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.tools.utils.RxJavaUtils;
import com.yeuyt.mygit.tools.utils.Utils;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private UserDataSource userDataSource;


    public LoginPresenter(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public void login(String name, String password) {
        if (!Utils.isNetworkAvailable(GitApplication.getContext())) {
            Utils.showToastLong("没有网络");
            return;
        }

        addDisposable(userDataSource.login(name, password)
            .compose(RxJavaUtils.<Boolean>applySchedulers())
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    getView().showOnSucess();
                }
            })
        );
    }

    @Override
    public String getLoginName() {
        return AccountHelper.getLoginUser(getView().getViewContext());
    }
}
