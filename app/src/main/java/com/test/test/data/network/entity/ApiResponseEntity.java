package com.test.test.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseEntity {
    @SerializedName("response")
    public List<WorkerApiEntity> workers;
}
