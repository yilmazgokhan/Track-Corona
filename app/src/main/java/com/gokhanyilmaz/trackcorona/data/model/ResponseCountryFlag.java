package com.gokhanyilmaz.trackcorona.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCountryFlag {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getName() {
        return name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getFlag() {
        return flag;
    }
}
