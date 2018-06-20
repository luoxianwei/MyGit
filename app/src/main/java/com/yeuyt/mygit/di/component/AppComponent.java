package com.yeuyt.mygit.di.component;

import android.content.Context;

import com.yeuyt.mygit.di.module.ContextModule;
import com.yeuyt.mygit.di.module.NetModule;
import com.yeuyt.mygit.model.net.dataSource.GankSource;
import com.yeuyt.mygit.model.net.dataSource.RepositorySource;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, ContextModule.class})
public interface AppComponent {

    Context getContext();

    UserDataSource getUserDataSource();
    RepositorySource getRepositorySource();
    GankSource getGankSource();
}
