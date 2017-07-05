package com.test.test.data.storage.mapper;

import com.test.test.data.storage.entity.SpecialtyStorageEntityEntity;
import com.test.test.domain.worker.Specialty;

import javax.inject.Inject;

public class SpecialtyStorageMapper {
    @Inject
    public SpecialtyStorageMapper() {
    }

    public SpecialtyStorageEntityEntity transform(Specialty specialty) {
        SpecialtyStorageEntityEntity entity = new SpecialtyStorageEntityEntity();
        entity.setId(specialty.getId());
        entity.setName(specialty.getName());
        return entity;
    }

    public Specialty transform(SpecialtyStorageEntityEntity entity) {
        Specialty specialty = new Specialty();
        specialty.setId(entity.getId());
        specialty.setName(entity.getName());
        return specialty;
    }
}
