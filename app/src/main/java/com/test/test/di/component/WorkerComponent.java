package com.test.test.di.component;

import com.test.test.di.PerPresenter;
import com.test.test.di.module.PresenterModule;
import com.test.test.di.module.WorkerModule;
import com.test.test.presentation.specialty.SpecialtiesListPresenter;
import com.test.test.presentation.splash.SplashPresenter;
import com.test.test.presentation.workerDetails.WorkerDetailsPresenter;
import com.test.test.presentation.workersList.WorkersListPresenter;

import dagger.Component;

@PerPresenter
@Component(dependencies = AppComponent.class, modules = {PresenterModule.class, WorkerModule.class})
public interface WorkerComponent {
    void inject(SpecialtiesListPresenter presenter);
    void inject(WorkersListPresenter presenter);
    void inject(WorkerDetailsPresenter presenter);
    void inject(SplashPresenter presenter);
}

