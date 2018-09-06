package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.di.GitApplication;
import com.yeuyt.mygit.model.entity.GankResponse;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.net.dataSource.GankSource;
import com.yeuyt.mygit.presenter.contract.ExploreContract;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.tools.utils.Utils;

import java.util.List;

import io.reactivex.functions.Consumer;



public class ExplorePresenter extends BasePresenter<ExploreContract.View> implements ExploreContract.Presenter {
    GankSource gankSource;

    public ExplorePresenter(GankSource gankSource) {
        this.gankSource = gankSource;
    }

    @Override
    public void getGankRepositories(int page) {
        if (!Utils.isNetworkAvailable(GitApplication.getContext())) {
            Utils.showToastLong("没有网络");
            getView().loadGankData(null);
            return;
        }
        addDisposable(
            gankSource.getGankRepositories(page)
                .subscribe(new Consumer<List<RepositoryInfo>>() {
                    @Override
                    public void accept(List<RepositoryInfo> repositoryInfos) throws Exception {
                        getView().loadGankData(repositoryInfos);
                    }
                })
        );
    }
}
