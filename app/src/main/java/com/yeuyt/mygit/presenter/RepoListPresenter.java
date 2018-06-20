package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.contract.RepoListContract;

import java.util.List;

import io.reactivex.functions.Consumer;


public class RepoListPresenter extends BasePresenter<RepoListContract.View> implements RepoListContract.Presenter{
    UserDataSource dataSource;

    public RepoListPresenter(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void userRepoList(String user, final int curPage) {
        addDisposable(
            dataSource.listOwnRepo(user, curPage)
            .subscribe(new Consumer<List<RepositoryInfo>>() {
                @Override
                public void accept(List<RepositoryInfo> repositoryInfos) throws Exception {
                    getView().loadRepoList(repositoryInfos);
                }
            })
        );
    }

    @Override
    public void staredRepoList(String user, int curPage) {
        addDisposable(
            dataSource.listStarredRepo(user, curPage)
                .subscribe(new Consumer<List<RepositoryInfo>>() {
                    @Override
                    public void accept(List<RepositoryInfo> repositoryInfos) throws Exception {
                        getView().loadRepoList(repositoryInfos);
                    }
                })
        );
    }

    @Override
    public void watchRepoList(String user, int curPage) {
        addDisposable(
            dataSource.listWatchingRepo(user, curPage)
                .subscribe(new Consumer<List<RepositoryInfo>>() {
                    @Override
                    public void accept(List<RepositoryInfo> repositoryInfos) throws Exception {
                        getView().loadRepoList(repositoryInfos);
                    }
                })
        );
    }
}
