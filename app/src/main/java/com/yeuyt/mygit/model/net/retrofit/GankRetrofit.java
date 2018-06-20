package com.yeuyt.mygit.model.net.retrofit;

import com.yeuyt.mygit.model.net.okhttp3.CacheOkHttpClient;
import com.yeuyt.mygit.tools.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class GankRetrofit extends BaseRetrofit {
    private CacheOkHttpClient client;

    public GankRetrofit(CacheOkHttpClient client) {
        this.client = client;
    }
    @Override
    protected String getBaseUrl() {
        return Constant.URL_GANK;
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
