package com.yeuyt.mygit.model.net.dataSource;


import android.text.TextUtils;

import com.yeuyt.mygit.model.entity.Branch;
import com.yeuyt.mygit.model.entity.ReadMe;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.SearchResult;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.model.net.service.RepositoryService;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.RxJavaUtils;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class RepositorySource {
    private RepositoryService repositoryService;

    public RepositorySource(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }


    public Observable<RepositoryInfo> getRepoInfo(String owner, String repo) {
        return repositoryService.getRepoInfo(owner, repo)
                .compose(RxJavaUtils.<RepositoryInfo>applySchedulers());
    }

    public Observable<List<RepositoryInfo>> listForks(String owner, String repo, int page) {
        return repositoryService.listForks(owner, repo, page)
                .compose(RxJavaUtils.<List<RepositoryInfo>>applySchedulers());
    }

    public Observable<List<UserEntity>> listWatchers(String owner, String repo, int page) {
        return repositoryService.listWatchers(owner, repo, page)
                .compose(RxJavaUtils.<List<UserEntity>>applySchedulers());
    }

    public Observable<List<UserEntity>> listStargazers(String owner, String repo, int page) {
        return repositoryService.listStargazers(owner, repo, page)
                .compose(RxJavaUtils.<List<UserEntity>>applySchedulers());
    }

    public Observable<List<UserEntity>> listContributors(String owner, String repo, int page) {
        return repositoryService.listContributors(owner, repo, page)
                .compose(RxJavaUtils.<List<UserEntity>>applySchedulers());
    }

    public Observable<RepositoryInfo> toFork(String owner, String repo) {
        return repositoryService.toFork(owner, repo)
                .compose(RxJavaUtils.<RepositoryInfo>applySchedulers());
    }

    public Observable<Boolean> toStar(String owner, String repo) {
        return repositoryService.toStar(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> toWatch(String owner, String repo) {
        return repositoryService.toWatch(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> unStar(String owner, String repo) {
        return repositoryService.unStar(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> unWatch(String owner, String repo) {
        return repositoryService.unWatch(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> checkStarred(String owner, String repo) {
        return repositoryService.checkStarred(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> checkWatching(String owner, String repo) {
        return repositoryService.checkWatching(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> delete(String owner, String repo) {
        return repositoryService.delete(owner, repo)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<List<Branch>> listBranches(String owner, String repo) {
        return repositoryService.listBranches(owner, repo)
                .compose(RxJavaUtils.<List<Branch>>applySchedulers());
    }

    public Observable<SearchResult<RepositoryInfo>> search(String keyWord, String language, int page) {
        if (keyWord == null)
            keyWord = "";
        if (!TextUtils.isEmpty(language))
            keyWord += language;
        return repositoryService.search(keyWord, page, Constant.PER_PAGE)
                .compose(RxJavaUtils.<SearchResult<RepositoryInfo>>applySchedulers());
    }

    public Observable<ReadMe> getReadMe(String owner, String repoName) {
        return repositoryService.getReadMe(owner, repoName)
                .compose(RxJavaUtils.<ReadMe>applySchedulers());
    }
}
