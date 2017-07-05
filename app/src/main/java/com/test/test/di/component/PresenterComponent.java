package com.test.test.di.component;

import com.arellomobile.mvp.MvpPresenter;
import com.test.test.di.PerActivity;
import com.test.test.di.module.PresenterModule;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = PresenterModule.class)
public interface PresenterComponent {

    MvpPresenter presenter();
}
