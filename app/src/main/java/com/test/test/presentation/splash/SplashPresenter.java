package com.test.test.presentation.splash;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.test.test.data.network.OfflineException;
import com.test.test.di.component.DaggerWorkerComponent;
import com.test.test.di.component.WorkerComponent;
import com.test.test.di.module.PresenterModule;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerInteractor;
import com.test.test.presentation.App;
import com.test.test.presentation.AppNavigator;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    @Inject
    WorkerInteractor workerInteractor;

    @Inject
    Router router;

    public SplashPresenter() {
        injectDependencies();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void syncWorkers() {
        workerInteractor.syncWorkers(new SyncWorkersObserver());

    }

    private class SyncWorkersObserver extends DisposableObserver<List<Worker>> {
        @Override
        public void onNext(List<Worker> workers) {
        }

        @Override
        public void onError(Throwable e) {
            if(e instanceof OfflineException) {
                getViewState().showConnectivityError();
            }
            else {
                getViewState().showError();
            }
            router.replaceScreen(AppNavigator.SPECIALTIES_SCREEN);
        }

        @Override
        public void onComplete() {
            router.replaceScreen(AppNavigator.SPECIALTIES_SCREEN);
        }
    }

    private void injectDependencies() {
        WorkerComponent component = DaggerWorkerComponent.builder()
                .presenterModule(new PresenterModule(this))
                .appComponent(App.INSTANCE.getAppComponent())
                .build();
        component.inject(this);
    }
}
