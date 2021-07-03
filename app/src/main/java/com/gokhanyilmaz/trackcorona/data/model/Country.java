package com.gokhanyilmaz.trackcorona.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huawei.hms.maps.model.LatLng;

public class Country {
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("dead")
    @Expose
    private Integer dead;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("updated")
    @Expose
    private String updated;

    public String getLocation() {
        return location;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public Integer getDead() {
        return dead;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public String getUpdated() {
        return updated;
    }

    /**
     * For get the location info as LatLng format
     */
    public LatLng getLocationAsLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }
}