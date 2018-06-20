package com.yeuyt.mygit.di.module;

import android.content.Context;

import com.yeuyt.mygit.model.net.dataSource.GankSource;
import com.yeuyt.mygit.model.net.dataSource.RepositorySource;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.model.net.okhttp3.GithubOkHttpClient;
import com.yeuyt.mygit.model.net.retrofit.GankRetrofit;
import com.yeuyt.mygit.model.net.retrofit.GithubAuthRetrofit;
import com.yeuyt.mygit.model.net.retrofit.GithubCommonRetrofit;
import com.yeuyt.mygit.model.net.service.GankService;
import com.yeuyt.mygit.model.net.service.RepositoryService;
import com.yeuyt.mygit.model.net.service.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetModule {

    @Provides
    @Singleton
    public UserDataSource proUserDataSource(GithubAuthRetrofit retrofit, UserService service , Context context) {
        return new UserDataSource(retrofit, service, context);
    }
    @Provides
    @Singleton
    public RepositorySource proRepositorySource(RepositoryService repositoryService) {
        return new RepositorySource(repositoryService);
    }
    @Provides
    @Singleton
    public GankSource proGankSource(GankService gankService, RepositoryService repositoryService) {
        return new GankSource(gankService, repositoryService);
    }
//-------------神奇的分割线-----------------------------------------

    @Provides
    public GithubAuthRetrofit proGithubAuthRetrofit() {
        return new GithubAuthRetrofit();
    }


    @Provides
    public UserService proUserService(GithubCommonRetrofit retrofit) {
        return  retrofit.build().create(UserService.class);
    }

    @Provides
    public RepositoryService proRepositoryService(GithubCommonRetrofit retrofit) {
        return retrofit.build().create(RepositoryService.class);
    }
    @Provides
    public GithubCommonRetrofit proGithubCommonRetrofit(GithubOkHttpClient client) {
        return new GithubCommonRetrofit(client);
    }

    @Provides
    public GankRetrofit proGankRetrofit(GithubOkHttpClient client) {
        return new GankRetrofit(client);
    }

    @Provides
    public GankService proGankService(GankRetrofit retrofit) {
        return retrofit.build().create(GankService.class);
    }
}
