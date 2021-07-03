package com.gokhanyilmaz.trackcorona.service;

import com.gokhanyilmaz.trackcorona.data.model.ResponseCoronaData;
import com.gokhanyilmaz.trackcorona.data.model.ResponseCountryFlag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequest {

    @GET("all")
    Call<List<ResponseCountryFlag>> getCountriesDataWithFlag();

    @GET("countries")
    Call<ResponseCoronaData> getCoronaAllCountries();

    @GET("countries/{countryCode}")
    Call<ResponseCoronaData> getCoronaData(@Path("countryCode") String countryCode);
}
