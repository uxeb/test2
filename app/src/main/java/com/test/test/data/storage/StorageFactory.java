package com.test.test.data.storage;

import android.content.Context;

import com.test.test.BuildConfig;
import com.test.test.data.storage.entity.Models;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

public class StorageFactory {
    public static ReactiveEntityStore<Persistable> create(Context context) {
        DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, 2);
        if (BuildConfig.DEBUG) {
            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = source.getConfiguration();
        return ReactiveSupport.toReactiveStore(
                new EntityDataStore<Persistable>(configuration));
    }
}
