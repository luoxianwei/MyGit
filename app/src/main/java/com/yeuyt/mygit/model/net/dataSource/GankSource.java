package com.yeuyt.mygit.model.net.dataSource;

import android.text.TextUtils;

import com.yeuyt.mygit.model.entity.GankResponse;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.net.service.GankService;
import com.yeuyt.mygit.model.net.service.RepositoryService;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.RxJavaUtils;
import com.yeuyt.mygit.tools.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

public class GankSource {
    private GankService gankService;
    private RepositoryService repositoryService;

    public GankSource(GankService gankService, RepositoryService repositoryService) {
        this.gankService = gankService;
        this.repositoryService = repositoryService;
    }

    public Observable<List<GankResponse.ResultsEntity>> getGankData(int page) {
        return gankService.getGankData(Constant.PER_PAGE, page)
            .compose(RxJavaUtils.<GankResponse>applySchedulers())
            .map(new Function<GankResponse, List<GankResponse.ResultsEntity>>() {
                @Override
                public List<GankResponse.ResultsEntity> apply(GankResponse gankResponse) throws Exception {
                    return gankResponse.results;
                }
            });
    }

    public Observable<RepositoryInfo> getGankRepository(int page) {
        return getGankData(page)
            .flatMap(new Function<List<GankResponse.ResultsEntity>, ObservableSource<RepositoryInfo>>() {
                @Override
                public ObservableSource<RepositoryInfo> apply(List<GankResponse.ResultsEntity> resultsEntities) throws Exception {
                    List<Observable<RepositoryInfo>> observableList = new ArrayList<>(20);
                    for (GankResponse.ResultsEntity resultsEntity : resultsEntities) {
                        String url = resultsEntity.getUrl();
                        if (TextUtils.isEmpty(url) || !url.startsWith("https://github.com/"))
                            continue;
                        String[] result = Utils.parseGithubUrl(url);
                        if (result == null || result.length != 2)
                            continue;
                        observableList.add(repositoryService.getRepoInfo(result[0], result[1]));
                    }
                    return Observable.mergeDelayError(observableList).compose(RxJavaUtils.<RepositoryInfo>applySchedulers());

                }
                });
    }

    public Observable<List<RepositoryInfo>> getGankRepositories(final int page) {
        final List<RepositoryInfo> repositories = new ArrayList<>(20);
        return Observable.create(new ObservableOnSubscribe<List<RepositoryInfo>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<RepositoryInfo>> emitter) throws Exception {
                getGankRepository(page)
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                emitter.onNext(repositories);
                            }
                        })
                        .subscribe(new Observer<RepositoryInfo>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext(RepositoryInfo repositoryInfo) {
                                repositories.add(repositoryInfo);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                emitter.onComplete();
                            }
                        });
            }
        });
    }
}
