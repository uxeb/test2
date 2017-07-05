package com.test.test.di.module;

import com.arellomobile.mvp.MvpPresenter;
import com.test.test.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    private final MvpPresenter presenter;

    public PresenterModule(MvpPresenter presenter) {
        this.presenter = presenter;
    }

    @Provides
    @PerActivity
    MvpPresenter presenter() {
        return this.presenter;
    }
}
