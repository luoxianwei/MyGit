package com.yeuyt.mygit.ui.interfaceView;

import android.content.Context;

public interface BaseView {
    int getLayoutId();
    void initView();
    void initData();
    Context getViewContext();
}
