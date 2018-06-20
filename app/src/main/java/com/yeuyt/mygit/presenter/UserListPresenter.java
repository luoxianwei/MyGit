package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.model.entity.UserInfo;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.contract.UserListContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UserListPresenter extends BasePresenter<UserListContract.View> implements UserListContract.Presenter {
    UserDataSource dataSource;

    public UserListPresenter(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void followingUserList(final String user, int curPage) {
        addDisposable(
            dataSource.listFollowing(user, curPage)
                .subscribe(new Consumer<List<UserEntity>>() {
                    @Override
                    public void accept(List<UserEntity> userEntities) throws Exception {
                        getView().loadUsersList(userEntities);
                    }
                })
        );
    }

    @Override
    public void followersUserList(String user, int curPage) {
        addDisposable(
                dataSource.listFollowers(user, curPage)
                        .subscribe(new Consumer<List<UserEntity>>() {
                            @Override
                            public void accept(List<UserEntity> userEntities) throws Exception {
                                getView().loadUsersList(userEntities);
                            }
                        })
        );
    }
}
