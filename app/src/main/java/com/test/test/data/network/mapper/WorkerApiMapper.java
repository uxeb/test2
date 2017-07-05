package com.test.test.data.network.mapper;

import com.test.test.data.network.entity.SpecialtyApiEntity;
import com.test.test.data.network.entity.WorkerApiEntity;
import com.test.test.domain.worker.Specialty;
import com.test.test.domain.worker.Worker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WorkerApiMapper {
    private SpecialtyApiMapper specialtyMapper;

    @Inject
    public WorkerApiMapper(SpecialtyApiMapper specialtyMapper) {
        this.specialtyMapper = specialtyMapper;
    }

    public Worker transform(WorkerApiEntity entity) {
        Worker worker = new Worker();
        worker.setAvatarUrl(entity.avatarUrl);
        worker.setBirthday(entity.birthday);
        worker.setFirstName(entity.firstName);
        worker.setLastName(entity.lastName);
        List<Specialty> specialties = new ArrayList<>();
        if(entity.specialties != null) {
            for(SpecialtyApiEntity specialtyEntity : entity.specialties) {
                specialties.add(specialtyMapper.transform(specialtyEntity));
            }
        }
        worker.setSpecialties(specialties);
        return worker;
    }
}
