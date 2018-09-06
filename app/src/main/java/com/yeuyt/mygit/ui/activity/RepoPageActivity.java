package com.yeuyt.mygit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.presenter.contract.RepoPageContract;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.helper.WebViewHelper;

import javax.inject.Inject;

public class RepoPageActivity extends BaseActivity implements RepoPageContract.View {
    private String mOwner;
    private String mRepo;
    @Inject
    RepoPageContract.Presenter presenter;

    WebViewHelper webViewHelper;
    private WebView webView;
    private ProgressBar progressBar;

    public static void launch(Activity srcActivity, String owner, String repoName) {
        Intent intent = new Intent(srcActivity, RepoPageActivity.class);
        intent.putExtra(Constant.USER_NAME, owner);
        intent.putExtra(Constant.REPO_NAME, repoName);
        srcActivity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repo_page;
    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webViewHelper = new WebViewHelper(webView, progressBar);
    }
    private void initDagger() {
        ComponentHolder.repoPageComponent.inject(this);
        presenter.attachView(this);
    }

    @Override
    public void initData() {
        initDagger();
        Intent intent = getIntent();
        if (intent != null) {
            mOwner = intent.getStringExtra(Constant.USER_NAME);
            mRepo = intent.getStringExtra(Constant.REPO_NAME);
            presenter.getReadMe(mOwner, mRepo);
        }
    }

    @Override
    public void loadReadMe(String url) {
        webViewHelper.loadUrl(url);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewHelper.detach();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onBackPressed() {
        if (!webViewHelper.goBackPage())
            super.onBackPressed();
    }
}
