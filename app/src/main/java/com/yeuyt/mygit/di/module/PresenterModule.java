package com.yeuyt.mygit.di.module;

import com.yeuyt.mygit.model.net.dataSource.GankSource;
import com.yeuyt.mygit.model.net.dataSource.RepositorySource;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.ExplorePresenter;
import com.yeuyt.mygit.presenter.LoginPresenter;
import com.yeuyt.mygit.presenter.NewsPresenter;
import com.yeuyt.mygit.presenter.RepoListPresenter;
import com.yeuyt.mygit.presenter.RepoPagePresenter;
import com.yeuyt.mygit.presenter.SearchListPresenter;
import com.yeuyt.mygit.presenter.UserListPresenter;
import com.yeuyt.mygit.presenter.contract.ExploreContract;
import com.yeuyt.mygit.presenter.contract.LoginContract;
import com.yeuyt.mygit.presenter.contract.NewsContract;
import com.yeuyt.mygit.presenter.contract.RepoListContract;
import com.yeuyt.mygit.presenter.contract.RepoPageContract;
import com.yeuyt.mygit.presenter.contract.SearchListContract;
import com.yeuyt.mygit.presenter.contract.UserListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @Provides
    public LoginContract.Presenter proLoginPresenter(UserDataSource userDataSource) {
        return new LoginPresenter(userDataSource);
    }

    @Provides
    public RepoListContract.Presenter proRepoListPresenter(UserDataSource userDataSource) {
        return new RepoListPresenter(userDataSource);
    }

    @Provides
    public UserListContract.Presenter proUserListPresenter(UserDataSource userDataSource) {
        return new UserListPresenter(userDataSource);
    }

    @Provides
    public SearchListContract.Presenter proSearchListPresenter(RepositorySource repositorySource, UserDataSource userDataSource) {
        return new SearchListPresenter(repositorySource, userDataSource);
    }

    @Provides
    public NewsContract.Presenter proNewsPresenter(UserDataSource userDataSource) {
        return new NewsPresenter(userDataSource);
    }

    @Provides
    public ExploreContract.Presenter proExplorePresenter(GankSource gankSource) {
        return new ExplorePresenter(gankSource);
    }

    @Provides
    public RepoPageContract.Presenter proRepoPagePresenter(RepositorySource repositorySource) {
        return new RepoPagePresenter(repositorySource);
    }
}
