package com.test.test.presentation.workersList;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.test.test.di.component.DaggerWorkerComponent;
import com.test.test.di.component.WorkerComponent;
import com.test.test.di.module.PresenterModule;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerInteractor;
import com.test.test.presentation.App;
import com.test.test.presentation.AppNavigator;
import com.test.test.utils.ExceptionLogger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class WorkersListPresenter extends MvpPresenter<WorkersListView> {

    @Inject
    WorkerInteractor workerInteractor;

    @Inject
    ExceptionLogger logger;

    @Inject
    Router router;

    public WorkersListPresenter() {
        injectDependencies();
    }

    public void loadWorkers(int specialtyId) {
        workerInteractor.getWorkers(specialtyId, new WorkersObserver());

    }

    public void onWorkerSelected(int workerId) {
        router.navigateTo(AppNavigator.WORKER_SCREEN, workerId);
    }

    @Override
    public void onDestroy() {
        workerInteractor.dispose();
        super.onDestroy();
    }

    private void injectDependencies() {
        WorkerComponent component = DaggerWorkerComponent.builder()
                .presenterModule(new PresenterModule(this))
                .appComponent(App.INSTANCE.getAppComponent())
                .build();
        component.inject(this);
    }

    private class WorkersObserver extends DisposableObserver<List<Worker>> {
        @Override
        public void onNext(List<Worker> workers) {
            if(workers.isEmpty()) {
                getViewState().showEmpty();
            }
            else {
                getViewState().showWorkers(workers);
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
