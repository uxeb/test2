package com.test.test.presentation.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.test.test.R;
import com.test.test.databinding.ActivitySplashBinding;
import com.test.test.di.component.ActivityComponent;
import com.test.test.di.component.DaggerActivityComponent;
import com.test.test.presentation.BaseActivity;


public class SplashActivity extends BaseActivity implements SplashView {

    private static final String TAG = "SplashActivity";

    @InjectPresenter
    SplashPresenter presenter;

    private ActivitySplashBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        presenter.syncWorkers();
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.common_error_text), Toast.LENGTH_LONG)
        .show();
    }

    @Override
    public void showConnectivityError() {
        Toast.makeText(this, getString(R.string.connectivity_error_text), Toast.LENGTH_LONG)
        .show();
    }

    @Override
    protected void injectDependencies() {
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
        component.inject(this);
    }
}
