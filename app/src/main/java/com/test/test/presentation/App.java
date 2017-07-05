package com.test.test.presentation;

import android.app.Application;
import android.os.StrictMode;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.test.test.BuildConfig;
import com.test.test.di.component.AppComponent;
import com.test.test.di.component.DaggerAppComponent;
import com.test.test.di.module.AppModule;

import java.io.InputStream;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {
    private AppComponent appComponent;

    public static App INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        INSTANCE = this;

        initCicerone();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDialog()
                    .build());
        }
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // Очищаем кэш изображений
        Glide.get(this).clearMemory();
    }
}
