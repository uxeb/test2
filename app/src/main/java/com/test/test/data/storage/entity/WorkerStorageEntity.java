package com.test.test.data.storage.entity;

import java.util.Set;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.JunctionTable;
import io.requery.Key;
import io.requery.ManyToMany;

@Entity
public abstract class WorkerStorageEntity {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Key @Generated
    public int id;

    public String avatarUrl;

    public String firstName;

    public String lastName;

    public String birthday;

    @ManyToMany
    @JunctionTable
    public Set<SpecialtyStorageEntityEntity> specialties;
}
