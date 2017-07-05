package com.test.test.presentation.splash;

import com.arellomobile.mvp.MvpView;

public interface SplashView extends MvpView {
    void showError();

    void showConnectivityError();
}
