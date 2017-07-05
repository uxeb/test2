package com.test.test.presentation.workerDetails;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.test.test.di.component.DaggerWorkerComponent;
import com.test.test.di.component.WorkerComponent;
import com.test.test.di.module.PresenterModule;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerInteractor;
import com.test.test.presentation.App;
import com.test.test.utils.ExceptionLogger;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class WorkerDetailsPresenter extends MvpPresenter<WorkerDetailsView> {

    @Inject
    WorkerInteractor workerInteractor;

    @Inject
    ExceptionLogger logger;

    public WorkerDetailsPresenter() {
        injectDependencies();
    }

    public void load(int workerId) {
        workerInteractor.getWorker(workerId, new WorkerObserver());
    }

    private void injectDependencies() {
        WorkerComponent component = DaggerWorkerComponent.builder()
                .presenterModule(new PresenterModule(this))
                .appComponent(App.INSTANCE.getAppComponent())
                .build();
        component.inject(this);
    }

    @Override
    public void onDestroy() {
        workerInteractor.dispose();
        super.onDestroy();
    }

    private class WorkerObserver extends DisposableObserver<Worker> {
        @Override
        public void onNext(Worker worker) {
            getViewState().showWorker(worker);
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
