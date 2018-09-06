package com.yeuyt.mygit.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeuyt.mygit.ui.interfaceView.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载
 */
public abstract class LazyFragment extends Fragment implements BaseView{
    private boolean hasInit = false;
    protected View rootView;
    private Bundle mSavedInstanceState;
    protected Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            this.mSavedInstanceState = savedInstanceState;
            unbinder = ButterKnife.bind(this, rootView);
            initView();
        }
        if (!shouldLazyLoad())
            loadData(getUserVisibleHint(), true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        loadData(isVisibleToUser, false);
    }

    private void loadData(boolean isVisibleToUser, boolean forceToLoad) {
        if (forceToLoad || (isVisibleToUser && !hasInit && shouldLazyLoad())) {
            Runnable runnable = new Runnable() {
                public void run() {
                    initData();
                    hasInit = true;
                }
            };
            new Handler().postDelayed(runnable, 500);
        }
    }

    protected boolean shouldLazyLoad() {
        return true;
    }


    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }
}
