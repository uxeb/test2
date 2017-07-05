package com.test.test.di.module;

import com.test.test.data.network.WorkerApiDataRepository;
import com.test.test.data.storage.WorkerStorageDataRepository;
import com.test.test.di.PerPresenter;
import com.test.test.domain.worker.WorkerApiRepository;
import com.test.test.domain.worker.WorkerStorageRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class WorkerModule {
    @Provides
    @PerPresenter
    WorkerApiRepository provideWorkerApiRepository(WorkerApiDataRepository repository) {
        return repository;
    }

    @Provides
    @PerPresenter
    WorkerStorageRepository provideWorkerStorageRepository(WorkerStorageDataRepository repository) {
        return repository;
    }
}
