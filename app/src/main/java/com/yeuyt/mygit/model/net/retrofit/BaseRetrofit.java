package com.yeuyt.mygit.model.net.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofit {

    public Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getBaseUrl())
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return enrichBuilder(builder).build();
    }

    protected abstract String getBaseUrl();
    protected abstract OkHttpClient getOkHttpClient();
    protected abstract Retrofit.Builder enrichBuilder(Retrofit.Builder builder);
}
