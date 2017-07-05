package com.test.test.domain.worker;

import java.util.List;

import io.reactivex.Observable;

public interface WorkerStorageRepository {
    Observable<List<Worker>> saveWorkers(List<Worker> workers);

    Observable<List<Worker>> getWorkers();

    Observable<List<Worker>> getWorkers(int specialtyId);

    Observable<Worker> getWorker(int id);

    Observable<List<Specialty>> getSpecialties();

    Observable<Integer> clearStore();
}
