package com.test.test.data.network;

import com.test.test.data.network.entity.ApiResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("testTask.json")
    Observable<ApiResponseEntity> getWorkers();
}
