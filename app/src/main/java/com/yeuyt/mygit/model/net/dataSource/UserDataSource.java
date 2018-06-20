package com.yeuyt.mygit.model.net.dataSource;

import android.content.Context;
import android.text.TextUtils;

import com.yeuyt.mygit.model.entity.Authorization;
import com.yeuyt.mygit.model.entity.CreateAuthorization;
import com.yeuyt.mygit.model.entity.Event;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.SearchResult;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.model.entity.UserInfo;
import com.yeuyt.mygit.model.net.retrofit.GithubAuthRetrofit;
import com.yeuyt.mygit.model.net.service.UserService;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.RxJavaUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class UserDataSource {
    private final GithubAuthRetrofit authRetrofit;
    private UserService userApi;

    private Context context;

    public UserDataSource(GithubAuthRetrofit authRetrofit, UserService userApi, Context ctx) {
        this.authRetrofit = authRetrofit;
        this.userApi = userApi;
        context = ctx;
    }


    public Observable<Boolean> login(final String userName, String password) {
        authRetrofit.setLogininfo(userName, password);
        final UserService api  = authRetrofit.build().create(UserService.class);
        CreateAuthorization create = new CreateAuthorization();
        create.client_id = Constant.GITHUB_CLIENT_ID;
        create.client_secret = Constant.GITHUB_CLIENT_SECRET;
        create.note = Constant.NOTE;
        create.scopes = Constant.SCOPES;

        return api.createAuthorization(create)
                .flatMap(new Function<Authorization, ObservableSource<UserInfo>>() {
                    @Override
                    public ObservableSource<UserInfo> apply(Authorization authorization) throws Exception {
                    String token = authorization.token;
                    AccountHelper.saveLoginToken(context, token);
                    return api.getUserInfo(userName);
                }})
                .map(new Function<UserInfo, Boolean>() {
                    @Override
                    public Boolean apply(UserInfo info) throws Exception {
                        if (info != null) {
                            AccountHelper.saveLoginUser(context, info);
                            return true;
                        }
                        return false;
                    }
                });
    }

    public Observable<UserInfo> getUserInfo(String user) {
        return userApi.getUserInfo(user).compose(RxJavaUtils.<UserInfo>applySchedulers());
    }
    public Observable<List<UserEntity>> listFollowers(String userName, int page) {
        return userApi.listFollowers(userName, page)
                .compose(RxJavaUtils.<List<UserEntity>>applySchedulers());
    }

    public Observable<List<UserEntity>> listFollowing(String user, int page) {
        return userApi.listFollowing(user, page)
                .compose(RxJavaUtils.<List<UserEntity>>applySchedulers());
    }

    public Observable<List<RepositoryInfo>> listStarredRepo(String user, int page) {
        return userApi.listStarredRepo(user, page)
                .compose(RxJavaUtils.<List<RepositoryInfo>>applySchedulers());
    }


    public Observable<List<RepositoryInfo>> listWatchingRepo(String user, int page) {
        return userApi.listWatchingRepo(user, page)
                .compose(RxJavaUtils.<List<RepositoryInfo>>applySchedulers());
    }

    public Observable<List<RepositoryInfo>> listOwnRepo(String user, int page) {
        return userApi.listOwnRepo(user, page)
                .compose(RxJavaUtils.<List<RepositoryInfo>>applySchedulers());
    }

    public Observable<List<Event>> listNews(String user, int page) {
        return userApi.listNews(user, page)
                .compose(RxJavaUtils.<List<Event>>applySchedulers());
    }

    public Observable<SearchResult<UserEntity>> search(String keyWord, String language, int page) {
        if (keyWord == null)
            keyWord = "";
        if (!TextUtils.isEmpty(language))
            keyWord += language;
        return userApi.search(keyWord, page, Constant.PER_PAGE)
                .compose(RxJavaUtils.<SearchResult<UserEntity>>applySchedulers());
    }

    public Observable<Boolean> signOut() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                AccountHelper.removeUserInfo(context);
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
    }

    public String getLoginUserName() {
        return AccountHelper.getLoginUser(context);
    }

    public Observable<Boolean> toFollow(String user) {
        return userApi.toFollow(user)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> toUnFollow(String user) {
        return userApi.toUnFollow(user)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }

    public Observable<Boolean> checkIfFollowing(String user) {
        return userApi.checkIfFollowing(user)
                .compose(RxJavaUtils.<Response<ResponseBody>>applySchedulers())
                .compose(RxJavaUtils.checkSuccessCode());
    }
}
