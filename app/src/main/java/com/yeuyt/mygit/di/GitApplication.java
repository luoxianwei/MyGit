package com.yeuyt.mygit.di;

import android.app.Application;
import android.content.Context;

import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.di.component.AppComponent;
import com.yeuyt.mygit.di.component.DaggerAppComponent;
import com.yeuyt.mygit.di.component.DaggerExploreComponent;
import com.yeuyt.mygit.di.component.DaggerListItemComponent;
import com.yeuyt.mygit.di.component.DaggerLoginComponent;
import com.yeuyt.mygit.di.component.DaggerNewsComponent;
import com.yeuyt.mygit.di.component.DaggerRepoPageComponent;
import com.yeuyt.mygit.di.component.DaggerSearchComponent;
import com.yeuyt.mygit.di.module.ContextModule;
import com.yeuyt.mygit.tools.utils.LogUtils;

public class GitApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LogUtils.init();
        initComoponet();

    }

    private void initComoponet() {
        AppComponent appComponent = DaggerAppComponent.builder().contextModule(new ContextModule(context)).build();

        ComponentHolder.appComponent = appComponent;
        ComponentHolder.context = context;
        ComponentHolder.loginComponent = DaggerLoginComponent.builder().appComponent(appComponent).build();
        ComponentHolder.listItemComponent = DaggerListItemComponent.builder().appComponent(appComponent).build();
        ComponentHolder.searchComponent = DaggerSearchComponent.builder().appComponent(appComponent).build();
        ComponentHolder.newsComponent = DaggerNewsComponent.builder().appComponent(appComponent).build();
        ComponentHolder.exploreComponent = DaggerExploreComponent.builder().appComponent(appComponent).build();
        ComponentHolder.repoPageComponent = DaggerRepoPageComponent.builder().appComponent(appComponent).build();
    }

    public static Context getContext() {
        return context;
    }
}
