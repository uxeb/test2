package com.test.test.data.storage.mapper;

import com.test.test.data.storage.entity.SpecialtyStorageEntityEntity;
import com.test.test.data.storage.entity.WorkerStorageEntityEntity;
import com.test.test.domain.worker.Specialty;
import com.test.test.domain.worker.Worker;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WorkerStorageMapper {
    private DateFormat dateFormat = new SimpleDateFormat(WorkerStorageEntityEntity.DATE_FORMAT);
    private SpecialtyStorageMapper specialtyMapper;

    @Inject
    public WorkerStorageMapper(SpecialtyStorageMapper specialtyMapper) {
        this.specialtyMapper = specialtyMapper;
    }

    public WorkerStorageEntityEntity transform(Worker worker) {
        WorkerStorageEntityEntity entity = new WorkerStorageEntityEntity();
        entity.setFirstName(worker.getFirstName());
        entity.setLastName(worker.getLastName());
        if(worker.getAvatarUrl() != null) {
            entity.setAvatarUrl(worker.getAvatarUrl().toString());
        }
        if(worker.getBirthday() != null) {
            entity.setBirthday(dateFormat.format(worker.getBirthday()));
        }
        for(Specialty specialty : worker.getSpecialties()) {
            entity.getSpecialties().add(specialtyMapper.transform(specialty));
        }
        return entity;
    }

    public Worker transform(WorkerStorageEntityEntity entity) throws MalformedURLException, ParseException{
        Worker worker = new Worker();
        worker.setId(entity.getId());
        worker.setFirstName(entity.getFirstName());
        worker.setLastName(entity.getLastName());
        if(entity.getAvatarUrl() != null) {
            worker.setAvatarUrl(new URL(entity.getAvatarUrl()));
        }
        if(entity.getBirthday() != null) {
            worker.setBirthday(dateFormat.parse(entity.getBirthday()));
        }
        List<Specialty> specialties = new ArrayList<>();
        if(entity.getSpecialties() != null) {
            for(SpecialtyStorageEntityEntity specialty : entity.getSpecialties()) {
                specialties.add(specialtyMapper.transform(specialty));
            }
        }
        worker.setSpecialties(specialties);
        return worker;
    }
}
