package com.yeuyt.mygit.di.component;

import com.yeuyt.mygit.di.DiScope;
import com.yeuyt.mygit.di.module.PresenterModule;
import com.yeuyt.mygit.ui.fragment.explore.ExploreFragment;

import dagger.Component;

@DiScope
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface ExploreComponent {
    void inject(ExploreFragment exploreFragment);
}
