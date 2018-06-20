package com.yeuyt.mygit.model.net.okhttp3;

import android.content.Context;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.tools.helper.AccountHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GithubOkHttpClient extends CacheOkHttpClient {

    @Inject
    public GithubOkHttpClient(Context context) {
        super(context);
    }

    public String getAcceptHeader() {
        return "application/vnd.github.v3+json";
    }
    @Override
    public OkHttpClient.Builder enrichBuilder(OkHttpClient.Builder builder) {
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", getAcceptHeader())
                        .header("User-Agent", context.getString(R.string.app_name));
                if (AccountHelper.isLogin(context)) {
                    requestBuilder.header("Authorization", "token " + AccountHelper.getLoginToken(context));
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return super.enrichBuilder(builder);
    }

}
