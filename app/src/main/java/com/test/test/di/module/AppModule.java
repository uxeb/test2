package com.test.test.di.module;

import android.content.Context;
import android.net.ConnectivityManager;

import com.test.test.data.network.Api;
import com.test.test.data.network.ApiFactory;
import com.test.test.data.storage.StorageFactory;
import com.test.test.presentation.App;
import com.test.test.utils.AndroidConnectivityChecker;
import com.test.test.utils.ConnectivityChecker;
import com.test.test.utils.ExceptionLogger;
import com.test.test.utils.FirebaseExceptionLogger;
import com.test.test.utils.GlideImageLoader;
import com.test.test.utils.ImageLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.app;
    }

    @Provides
    @Singleton
    Api provideApi(@Named("api_base_url") String baseUrl, ConnectivityChecker connectivityChecker) {
        return ApiFactory.create(baseUrl, connectivityChecker);
    }

    @Provides
    @Named("api_base_url")
    String provideApiBaseUrl() {
            return "http://178.62.196.215/images/";
    }

    @Provides
    @Singleton
    ExceptionLogger provideExceptionLogger() {
        return new FirebaseExceptionLogger();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return new GlideImageLoader(app);
    }

    @Provides
    @Singleton
    ReactiveEntityStore<Persistable> provideStorage() {
        return StorageFactory.create(app);
    }

    @Provides
    @Singleton
    ConnectivityChecker provideConnectivityChecker() {
        return new AndroidConnectivityChecker(
                (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE));
    }
}
