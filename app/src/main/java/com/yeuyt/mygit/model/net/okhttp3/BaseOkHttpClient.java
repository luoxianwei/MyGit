package com.yeuyt.mygit.model.net.okhttp3;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public abstract class BaseOkHttpClient {
    public OkHttpClient build() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS);
        return enrichBuilder(builder).build();
    }

    public abstract OkHttpClient.Builder enrichBuilder(OkHttpClient.Builder builder);
}
