package com.test.test.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.test.test.R;
import com.test.test.di.component.AppComponent;
import com.test.test.di.module.ActivityModule;

import javax.inject.Inject;

import ru.terrakok.cicerone.NavigatorHolder;

public abstract class BaseActivity extends MvpAppCompatActivity {

    public AppComponent getAppComponent() {
        return App.INSTANCE.getAppComponent();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Inject
    NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(new AppNavigator(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    public void showError() {
        new MaterialDialog.Builder(this)
                .title(R.string.error_dialog_title)
                .content(R.string.error_dialog_content)
                .positiveText(R.string.dialog_agree)
                .show();
    }

    protected abstract void injectDependencies();
}
