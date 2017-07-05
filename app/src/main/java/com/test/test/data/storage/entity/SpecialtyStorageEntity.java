package com.test.test.data.storage.entity;

import java.util.Set;

import io.requery.Entity;
import io.requery.Key;
import io.requery.ManyToMany;

@Entity
public abstract class SpecialtyStorageEntity {
    @Key
    public int id;

    public String name;

    @ManyToMany
    public Set<WorkerStorageEntityEntity> workers;
}
