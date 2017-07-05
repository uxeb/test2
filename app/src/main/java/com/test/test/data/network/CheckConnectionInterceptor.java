package com.test.test.data.network;

import com.test.test.utils.ConnectivityChecker;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Выбрасывает исключение, если нет подключения к интернету
 */
public class CheckConnectionInterceptor implements Interceptor {
    private static final String TAG = "CheckConnection";

    private ConnectivityChecker connectivityChecker;

    @Inject
    public CheckConnectionInterceptor(ConnectivityChecker connectivityChecker) {
        this.connectivityChecker = connectivityChecker;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!connectivityChecker.check()) {
            throw new OfflineException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
