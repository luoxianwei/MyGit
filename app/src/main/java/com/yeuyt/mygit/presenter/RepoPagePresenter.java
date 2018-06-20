package com.yeuyt.mygit.presenter;

import com.yeuyt.mygit.model.entity.ReadMe;
import com.yeuyt.mygit.model.net.dataSource.RepositorySource;
import com.yeuyt.mygit.presenter.contract.RepoPageContract;

import io.reactivex.functions.Consumer;

public class RepoPagePresenter extends BasePresenter<RepoPageContract.View> implements RepoPageContract.Presenter{
    private RepositorySource repositorySource;

    public RepoPagePresenter(RepositorySource repositorySource) {
        this.repositorySource = repositorySource;
    }

    @Override
    public void getReadMe(String owner, String repoName) {
        addDisposable(
            repositorySource.getReadMe(owner, repoName)
                .subscribe(new Consumer<ReadMe>() {
                    @Override
                    public void accept(ReadMe readMe) throws Exception {
                        getView().loadReadMe(readMe.html_url);
                    }
                })
        );

    }
}
