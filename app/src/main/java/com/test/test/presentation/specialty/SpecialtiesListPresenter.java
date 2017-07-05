package com.test.test.presentation.specialty;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.test.test.di.component.DaggerWorkerComponent;
import com.test.test.di.component.WorkerComponent;
import com.test.test.di.module.PresenterModule;
import com.test.test.domain.worker.Specialty;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerInteractor;
import com.test.test.presentation.App;
import com.test.test.presentation.AppNavigator;
import com.test.test.utils.ExceptionLogger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class SpecialtiesListPresenter extends MvpPresenter<SpecialtiesListView> {
    private static final String TAG = "SpecialtiesListPresente";

    @Inject
    WorkerInteractor workerInteractor;

    @Inject
    ExceptionLogger logger;

    public SpecialtiesListPresenter() {
        injectDependencies();
    }

    public void load() {
        workerInteractor.getSpecialties(new SpecialtiesObserver());
    }

    @Override
    public void onDestroy() {
        workerInteractor.dispose();
        super.onDestroy();
    }

    public void onSpecialtySelected(int specialtyId) {
        App.INSTANCE.getRouter().navigateTo(AppNavigator.WORKERS_SCREEN, specialtyId);
    }

    private void injectDependencies() {
        WorkerComponent component = DaggerWorkerComponent.builder()
                .presenterModule(new PresenterModule(this))
                .appComponent(App.INSTANCE.getAppComponent())
                .build();
        component.inject(this);
    }

    private class SpecialtiesObserver extends DisposableObserver<List<Specialty>> {
        @Override
        public void onNext(List<Specialty> specialties) {
            if(specialties.isEmpty()) {
                getViewState().showEmpty();
            }
            else {
                getViewState().showSpecialties(specialties);
            }
        }

        @Override
        public void onError(Throwable e) {
            getViewState().showError();
            logger.sendReport(e);
        }

        @Override
        public void onComplete() {

        }
    }
}
