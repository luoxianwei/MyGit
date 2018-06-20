package com.yeuyt.mygit.ui.interfaceView;

import android.content.Context;
import android.os.Bundle;

public interface BaseView {
    int getLayoutId();
    void initView();
    void initData(Bundle savedInstanceState);
    Context getViewContext();
}
