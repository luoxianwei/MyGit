package com.yeuyt.mygit.model.net.retrofit;

import com.yeuyt.mygit.model.net.okhttp3.AuthOkHttpClient;
import com.yeuyt.mygit.tools.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class GithubAuthRetrofit extends BaseRetrofit{
    private String name;
    private String password;

    public void setLogininfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    protected String getBaseUrl() {
        return Constant.URL_GITHUB;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        return new AuthOkHttpClient(name, password).build();
    }

    @Override
    protected Retrofit.Builder enrichBuilder(Retrofit.Builder builder) {
        return builder;
    }
}
