package com.yeuyt.mygit.model.net.okhttp3;

import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthOkHttpClient extends BaseOkHttpClient{
    private String userName;
    private String password;

    public AuthOkHttpClient(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public OkHttpClient.Builder enrichBuilder(OkHttpClient.Builder builder) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String userCredentials = userName + ":" + password;
                    String basicAuth =
                            "Basic " + new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basicAuth.trim());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        return builder;
    }

}
