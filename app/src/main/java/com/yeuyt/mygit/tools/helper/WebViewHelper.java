package com.yeuyt.mygit.tools.helper;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class WebViewHelper {
    private WebView webView;
    private ProgressBar progressBar;
    private WebViewCallback callback;
    private Disposable disposable;
    private String url;

    public abstract static class WebViewCallback {
        void onPageStarted() {
        }

        void onPageFinished() {
        }

        public abstract void onError();
    }

    public String getUrl() {
        return url;
    }

    public void setCallback(WebViewCallback callback) {
        this.callback = callback;
    }

    public WebViewHelper(WebView webView, ProgressBar progressBar) {
        this.webView = webView;
        this.progressBar = progressBar;
        init();
    }

    public void reLoad() {
        if (TextUtils.isEmpty(url))
            return;
        loadUrl(url);
    }

    public void loadUrl(String url) {
        this.url = url;
        webView.loadUrl(url);
        progressBar.setVisibility(VISIBLE);
        if (disposable != null)
            disposable.dispose();
        disposable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("Progress", "call() called with: progress = [" + progressBar.getProgress() + "]");
                        if (progressBar.getProgress() >= 90)
                            return;
                        progressBar.setProgress(progressBar.getProgress() + 5);
                    }
                });

    }

    public void init() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                if (callback != null) {
                    callback.onError();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (callback != null) {
                    callback.onError();
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (callback != null)
                    callback.onError();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                disposable.dispose();
                progressBar.setProgress(100);
                progressBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(INVISIBLE);
                        progressBar.setProgress(0);
                    }
                }, 200);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar.getVisibility() == INVISIBLE)
                    progressBar.setVisibility(VISIBLE);
                if (newProgress != 100) {
                    progressBar.setProgress(newProgress);
                }
            }
        });

        //setting
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    public boolean goBackPage() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    public void detach() {
        if (disposable != null)
            disposable.dispose();
        callback = null;
    }
}
