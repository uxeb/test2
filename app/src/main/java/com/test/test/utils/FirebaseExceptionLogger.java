package com.test.test.utils;

import com.test.test.BuildConfig;
import com.test.test.data.network.OfflineException;

import javax.inject.Inject;

import io.reactivex.exceptions.CompositeException;

public class FirebaseExceptionLogger implements ExceptionLogger {

    @Inject
    public FirebaseExceptionLogger() {
    }

    public void sendReport(Throwable e) {
        if(!isConnectivityError(e)) {
            // отправляем информацию об ошибке в firebase, если это не ошибка подключения к интернету
//            FirebaseCrash.report(e);
        }
        if(BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }

    private boolean isConnectivityError(Throwable e) {
        if(e instanceof OfflineException) {
            return true;
        }
        if(e instanceof CompositeException) {
            CompositeException ex = (CompositeException) e;
            for(Throwable throwable : ex.getExceptions()) {
                if(throwable instanceof OfflineException) {
                    return true;
                }
            }
        }
        return false;
    }
}
