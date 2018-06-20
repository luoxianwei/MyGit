package com.yeuyt.mygit.di.component;

import com.yeuyt.mygit.di.DiScope;
import com.yeuyt.mygit.di.module.PresenterModule;
import com.yeuyt.mygit.ui.activity.detail_list.RepoListActivity;
import com.yeuyt.mygit.ui.activity.detail_list.UserListActivity;

import dagger.Component;

@DiScope
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface ListItemComponent {
    void inject(RepoListActivity activity);
    void inject(UserListActivity activity);
}
