package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.model.entity.GankResponse;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.net.dataSource.GankSource;
import com.yeuyt.mygit.presenter.contract.ExploreContract;

import java.util.List;

import io.reactivex.functions.Consumer;



public class ExplorePresenter extends BasePresenter<ExploreContract.View> implements ExploreContract.Presenter {
    GankSource gankSource;

    public ExplorePresenter(GankSource gankSource) {
        this.gankSource = gankSource;
    }

    @Override
    public void getGankRepositories(int page) {
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
