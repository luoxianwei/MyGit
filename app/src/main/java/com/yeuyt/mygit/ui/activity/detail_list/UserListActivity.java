package com.yeuyt.mygit.ui.activity.detail_list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.presenter.contract.UserListContract;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ListUserAdapter;

import javax.inject.Inject;

public abstract class UserListActivity extends BaseListActivity implements UserListContract.View {
    protected String user;
    protected int curPage = 1;
    protected ListUserAdapter adapter;

    @Inject
    protected UserListContract.Presenter presenter;

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

        adapter = new ListUserAdapter(R.layout.item_user);
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
                    case R.id.tv_follow:
                        Utils.showToastLong("tv_follow");
                        break;
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        rv.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
