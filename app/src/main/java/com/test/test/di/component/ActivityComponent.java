package com.test.test.di.component;

import android.app.Activity;

import com.test.test.di.PerActivity;
import com.test.test.di.module.ActivityModule;
import com.test.test.presentation.specialty.SpecialtiesListActivity;
import com.test.test.presentation.splash.SplashActivity;
import com.test.test.presentation.workerDetails.WorkerDetailsActivity;
import com.test.test.presentation.workersList.WorkersListActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SpecialtiesListActivity activity);
    void inject(WorkersListActivity activity);
    void inject(WorkerDetailsActivity activity);
    void inject(SplashActivity activity);

    Activity activity();
}
