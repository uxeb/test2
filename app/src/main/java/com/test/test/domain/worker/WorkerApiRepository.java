package com.test.test.domain.worker;

import java.util.List;

import io.reactivex.Observable;

public interface WorkerApiRepository {
    Observable<List<Worker>> getWorkers();
}
