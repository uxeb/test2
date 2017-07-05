package com.test.test.data.network;

import com.test.test.data.network.entity.ApiResponseEntity;
import com.test.test.data.network.entity.WorkerApiEntity;
import com.test.test.data.network.mapper.WorkerApiMapper;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerApiRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class WorkerApiDataRepository implements WorkerApiRepository {
    private static final String TAG = "WorkerDataRepository";

    private Api api;
    private WorkerApiMapper workerApiMapper;

    @Inject
    public WorkerApiDataRepository(Api api,
                                   WorkerApiMapper workerApiMapper) {
        this.api = api;
        this.workerApiMapper = workerApiMapper;
    }

    @Override
    public Observable<List<Worker>> getWorkers() {
        Observable<List<Worker>> obs = api.getWorkers().map(new Function<ApiResponseEntity, List<Worker>>() {
            @Override
            public List<Worker> apply(@NonNull ApiResponseEntity apiResponseEntity) throws Exception {
                List<Worker> workers = new ArrayList<>();
                if(apiResponseEntity.workers != null) {
                    for(WorkerApiEntity workerEntity : apiResponseEntity.workers) {
                        workers.add(workerApiMapper.transform(workerEntity));
                    }
                }
                return workers;
            }
        });
        return obs;
    }
}
