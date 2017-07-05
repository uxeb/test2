package com.test.test.data.storage;

import com.test.test.data.storage.entity.SpecialtyStorageEntityEntity;
import com.test.test.data.storage.entity.WorkerStorageEntityEntity;
import com.test.test.data.storage.entity.WorkerStorageEntityEntity_SpecialtyStorageEntityEntity;
import com.test.test.data.storage.mapper.SpecialtyStorageMapper;
import com.test.test.data.storage.mapper.WorkerStorageMapper;
import com.test.test.domain.worker.Specialty;
import com.test.test.domain.worker.Worker;
import com.test.test.domain.worker.WorkerStorageRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

public class WorkerStorageDataRepository implements WorkerStorageRepository {
    private static final String TAG = "WorkerDataRepository";

    private ReactiveEntityStore<Persistable> store;
    private WorkerStorageMapper workerStorageMapper;
    private SpecialtyStorageMapper specialtyStorageMapper;

    @Inject
    public WorkerStorageDataRepository(WorkerStorageMapper workerStorageMapper,
                                       SpecialtyStorageMapper specialtyStorageMapper,
                                       ReactiveEntityStore<Persistable> store) {
        this.workerStorageMapper = workerStorageMapper;
        this.specialtyStorageMapper = specialtyStorageMapper;
        this.store = store;
    }

    @Override
    public Observable<List<Worker>> saveWorkers(final List<Worker> workers) {
        List<WorkerStorageEntityEntity> entities = new ArrayList<>();
        if (workers != null) {
            for(Worker worker : workers) {
                entities.add(workerStorageMapper.transform(worker));
            }
        }
        Observable<List<Worker>> obs = store.insert(entities).toObservable()
                .map(new Function<Iterable<WorkerStorageEntityEntity>, List<Worker>>() {
                    @Override
                    public List<Worker> apply(@NonNull Iterable<WorkerStorageEntityEntity> workerStorageEntityEntities) throws Exception {
                        return workers;
                    }
                });
        return obs;
    }

    @Override
    public Observable<List<Worker>> getWorkers() {
        Observable<List<Worker>> obs = store
                .select(WorkerStorageEntityEntity.class)
                .orderBy(WorkerStorageEntityEntity.LAST_NAME.asc())
                .get().observable()
                .map(new Function<WorkerStorageEntityEntity, Worker>() {
                    @Override
                    public Worker apply(@NonNull WorkerStorageEntityEntity entity) throws Exception {
                        return workerStorageMapper.transform(entity);
                    }
                })
                .toList()
                .toObservable();
        return obs;
    }

    @Override
    public Observable<List<Worker>> getWorkers(int specialtyId) {
        Observable<List<Worker>> obs = store
                .select(WorkerStorageEntityEntity.class).distinct()
                .join(WorkerStorageEntityEntity_SpecialtyStorageEntityEntity.class).on(WorkerStorageEntityEntity.ID.eq(WorkerStorageEntityEntity_SpecialtyStorageEntityEntity.WORKER_STORAGE_ENTITY_ID))
                .join(SpecialtyStorageEntityEntity.class).on(SpecialtyStorageEntityEntity.ID.eq(WorkerStorageEntityEntity_SpecialtyStorageEntityEntity.SPECIALTY_STORAGE_ENTITY_ID))
                .where(SpecialtyStorageEntityEntity.ID.eq(specialtyId))
                .orderBy(WorkerStorageEntityEntity.LAST_NAME.asc())
                .get().observable()
                .map(new Function<WorkerStorageEntityEntity, Worker>() {
                    @Override
                    public Worker apply(@NonNull WorkerStorageEntityEntity entity) throws Exception {
                        return workerStorageMapper.transform(entity);
                    }
                })
                .toList()
                .toObservable();
        return obs;
    }

    @Override
    public Observable<Worker> getWorker(int id) {
        Observable<Worker> obs = store
                .select(WorkerStorageEntityEntity.class)
                .where(WorkerStorageEntityEntity.ID.eq(id))
                .get().observable()
                .map(new Function<WorkerStorageEntityEntity, Worker>() {
                    @Override
                    public Worker apply(@NonNull WorkerStorageEntityEntity entity) throws Exception {
                        return workerStorageMapper.transform(entity);
                    }
                });
        return obs;
    }

    @Override
    public Observable<List<Specialty>> getSpecialties() {
        Observable<List<Specialty>> obs = store
                .select(SpecialtyStorageEntityEntity.class)
                .orderBy(SpecialtyStorageEntityEntity.NAME.asc())
                .get().observable()
                .map(new Function<SpecialtyStorageEntityEntity, Specialty>() {
                    @Override
                    public Specialty apply(@NonNull SpecialtyStorageEntityEntity entity) throws Exception {
                        return specialtyStorageMapper.transform(entity);
                    }
                })
                .toList()
                .toObservable();
        return obs;
    }

    @Override
    public Observable<Integer> clearStore() {
        Observable<Integer> deleteWorkers = store.delete(WorkerStorageEntityEntity.class).get().single().toObservable();
        Observable<Integer> deleteSpecialties = store.delete(SpecialtyStorageEntityEntity.class).get().single().toObservable();

        return deleteWorkers.concatWith(deleteSpecialties);
    }
}
