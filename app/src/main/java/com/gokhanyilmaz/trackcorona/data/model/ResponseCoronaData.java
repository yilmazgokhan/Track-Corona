package com.gokhanyilmaz.trackcorona.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCoronaData {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private List<Country> data = null;

    public Integer getCode() {
        return code;
    }

    public List<Country> getData() {
        return data;
    }
}
