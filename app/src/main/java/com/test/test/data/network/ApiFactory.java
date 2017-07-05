package com.test.test.data.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.test.test.BuildConfig;
import com.test.test.utils.ConnectivityChecker;

import java.net.URL;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    public static Api create(String baseUrl, ConnectivityChecker connectivityChecker) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new CheckConnectionInterceptor(connectivityChecker));
        if(BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(new StethoInterceptor());
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(URL.class, new UrlDeserializer());
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.client(httpClientBuilder.build());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));

        Retrofit retrofit = builder.build();
        return retrofit.create(Api.class);
    }
}
