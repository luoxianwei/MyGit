package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.SearchResult;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.model.net.dataSource.RepositorySource;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.contract.SearchListContract;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.tools.utils.LogUtils;
import com.yeuyt.mygit.tools.utils.Utils;


import io.reactivex.functions.Consumer;

public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter{
    private RepositorySource repositorySource;
    private UserDataSource userDataSource;

    public SearchListPresenter(RepositorySource repositorySource, UserDataSource userDataSource) {
        this.repositorySource = repositorySource;
        this.userDataSource = userDataSource;
    }


    @Override
    public void searchUser(String keyword, int curPage) {
        if (!AccountHelper.isLogin(getView().getViewContext())) {
            Utils.showToastLong("请先登陆");
            return;
        }

        addDisposable(
            userDataSource.search(keyword, "+language:any language", curPage)
            .subscribe(new Consumer<SearchResult<UserEntity>>() {
                @Override
                public void accept(SearchResult<UserEntity> userEntitySearchResult) throws Exception {
                    getView().loadUsersList(userEntitySearchResult);
                }
            })
        );
    }

    @Override
    public void searchRepository(String keyword, int curPage) {
        if (!AccountHelper.isLogin(getView().getViewContext())) {
            Utils.showToastLong("请先登陆");
            return;
        }
        addDisposable(
            repositorySource.search(keyword, "+language:any language", curPage)
                .subscribe(new Consumer<SearchResult<RepositoryInfo>>() {
                    @Override
                    public void accept(SearchResult<RepositoryInfo> repositoryInfoSearchResult) throws Exception {
                        LogUtils.i("SearchResult<RepositoryInfo>");
                        getView().loadRepositoriesList(repositoryInfoSearchResult);
                    }
                })
        );

    }
}
