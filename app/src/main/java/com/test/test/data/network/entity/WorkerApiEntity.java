package com.test.test.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class WorkerApiEntity {
    @SerializedName("avatr_url")
    public URL avatarUrl;

    @SerializedName("f_name")
    public String firstName;

    @SerializedName("l_name")
    public String lastName;

    @SerializedName("birthday")
    public Date birthday;

    @SerializedName("specialty")
    public List<SpecialtyApiEntity> specialties;
}
