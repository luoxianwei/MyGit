package com.yeuyt.mygit.ui.activity.detail_list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.presenter.contract.RepoListContract;
import com.yeuyt.mygit.tools.Constant;

import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ListRepoAdapter;

import javax.inject.Inject;

public abstract class RepoListActivity extends BaseListActivity implements RepoListContract.View {
    protected String user;
    protected int curPage = 1;
    protected ListRepoAdapter adapter;

    @Inject
    protected RepoListContract.Presenter presenter;

    protected void initDagger() {
        ComponentHolder.listItemComponent.inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void getInfoIntent() {
        Intent intent= getIntent();
        if (intent != null) {
            user = intent.getStringExtra(Constant.USER_NAME);
        }
    }
    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }
    @Override
    protected void bindRvAdapter() {

        adapter = new ListRepoAdapter(R.layout.item_repo);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Utils.showToastLong("默认操作");
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.tv_stars:
                        Utils.showToastLong("tv_stars");
                        break;
                    case R.id.tv_forks:
                        Utils.showToastLong("tv_stars");
                        break;
                    case R.id.tv_watches:
                        Utils.showToastLong("tv_stars");
                        break;
                }
            }
        });
        rv.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
