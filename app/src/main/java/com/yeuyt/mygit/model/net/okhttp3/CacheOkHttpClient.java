package com.yeuyt.mygit.model.net.okhttp3;

import android.content.Context;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.tools.utils.Utils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CacheOkHttpClient extends BaseOkHttpClient{
    public static final long SIZE_OF_CACHE = 1024*1024*50;
    protected Context context;

    @Inject
    public CacheOkHttpClient(Context context) {
        this.context = context;
    }

    @Override
    public OkHttpClient.Builder enrichBuilder(OkHttpClient.Builder builder) {
        File file = new File(context.getCacheDir(), context.getString(R.string.app_name));
        Cache cache = new Cache(file, SIZE_OF_CACHE);
        return builder.cache(cache).addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (Utils.isNetworkAvailable(context)) {
                    return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=600")
                        .build();
                } else {
                    return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                        .build();
                }
            }
        }).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!Utils.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                }
                return chain.proceed(request);
            }
        });

    }
}
