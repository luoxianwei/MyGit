package com.yeuyt.mygit.model.net.retrofit;

import com.yeuyt.mygit.model.net.okhttp3.GithubOkHttpClient;
import com.yeuyt.mygit.tools.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class GithubCommonRetrofit extends BaseRetrofit {

    private GithubOkHttpClient client;

    public GithubCommonRetrofit(GithubOkHttpClient client) {
        this.client = client;
    }

    @Override
    protected String getBaseUrl() {
        return Constant.URL_GITHUB;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        return client.build();
    }

    @Override
    protected Retrofit.Builder enrichBuilder(Retrofit.Builder builder) {
        return builder;
    }
}
