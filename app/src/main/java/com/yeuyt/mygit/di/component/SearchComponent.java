package com.yeuyt.mygit.di.component;

import com.yeuyt.mygit.di.DiScope;
import com.yeuyt.mygit.di.module.PresenterModule;
import com.yeuyt.mygit.ui.fragment.search.SearchFragment;

import dagger.Component;

@DiScope
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface SearchComponent {
    void inject(SearchFragment fragment);
}
