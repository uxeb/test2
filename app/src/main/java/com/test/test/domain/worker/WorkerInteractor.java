package com.test.test.domain.worker;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WorkerInteractor {

    private WorkerApiRepository apiRepository;
    private WorkerStorageRepository storageRepository;

    private CompositeDisposable compositeDisposable;

    private Scheduler subscribeScheduler;
    private Scheduler observeScheduler;

    @Inject
    public WorkerInteractor(WorkerApiRepository apiRepository,
                            WorkerStorageRepository storageRepository) {
        this.apiRepository = apiRepository;
        this.storageRepository = storageRepository;
        this.compositeDisposable = new CompositeDisposable();
        // TODO: 02.07.17 заинжектить шедулеры
        this.subscribeScheduler = Schedulers.io();
        this.observeScheduler = AndroidSchedulers.mainThread();
    }


    public void getWorkers(DisposableObserver<List<Worker>> observer) {
        subscribeWith(observer, storageRepository.getWorkers());
    }

    public void getWorker(int workerId, DisposableObserver<Worker> observer) {
        subscribeWith(observer, storageRepository.getWorker(workerId));
    }

    public void getWorkers(int specialtyId, DisposableObserver<List<Worker>> observer) {
        subscribeWith(observer, storageRepository.getWorkers(specialtyId));
    }

    public void getSpecialties(DisposableObserver<List<Specialty>> observer) {
        subscribeWith(observer, storageRepository.getSpecialties());
    }



    public void syncWorkers(DisposableObserver<List<Worker>> observer) {
        Observable<List<Worker>> observable = apiRepository.getWorkers()
                .flatMap(new Function<List<Worker>, ObservableSource<List<Worker>>>() {
                    @Override
                    public ObservableSource<List<Worker>> apply(@NonNull final List<Worker> workers) throws Exception {
                        return storageRepository.clearStore().toList().toObservable()
                                .map(new Function<List<Integer>, List<Worker>>() {
                                    @Override
                                    public List<Worker> apply(@NonNull List<Integer> integers) throws Exception {
                                        return workers;
                                    }
                                });
                    }
                })
                .flatMap(new Function<List<Worker>, ObservableSource<List<Worker>>>() {
                    @Override
                    public ObservableSource<List<Worker>> apply(@NonNull List<Worker> workers) throws Exception {
                        return storageRepository.saveWorkers(workers);
                    }
                });

        subscribeWith(observer, observable);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    private <T> void subscribeWith(DisposableObserver<T> observer, Observable<T> observable) {
        Disposable disposable = observable
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribeWith(observer);
        compositeDisposable.add(disposable);
    }
}
