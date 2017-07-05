package com.test.test.data.network.mapper;

import com.test.test.data.network.entity.SpecialtyApiEntity;
import com.test.test.domain.worker.Specialty;

import javax.inject.Inject;

public class SpecialtyApiMapper {

    @Inject
    public SpecialtyApiMapper() {
    }

    public Specialty transform(SpecialtyApiEntity entity) {
        Specialty specialty = new Specialty();
        specialty.setId(entity.id);
        specialty.setName(entity.name);
        return specialty;
    }
}
