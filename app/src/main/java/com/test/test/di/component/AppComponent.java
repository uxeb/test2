package com.test.test.di.component;

import com.test.test.data.network.Api;
import com.test.test.di.module.AppModule;
import com.test.test.utils.ConnectivityChecker;
import com.test.test.utils.ExceptionLogger;
import com.test.test.utils.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

//    void inject(PushService pushService);

    Api api();
    ExceptionLogger logger();
    ReactiveEntityStore<Persistable> storage();
    ImageLoader imageLoader();
    ConnectivityChecker connectivityChecker();
}
