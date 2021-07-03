package com.gokhanyilmaz.trackcorona.ui.main;

import android.util.Log;

import com.gokhanyilmaz.trackcorona.data.AdapterItem;
import com.gokhanyilmaz.trackcorona.data.CountryInfo;
import com.gokhanyilmaz.trackcorona.data.model.Country;
import com.gokhanyilmaz.trackcorona.data.model.ResponseCoronaData;
import com.gokhanyilmaz.trackcorona.data.model.ResponseCountryFlag;
import com.gokhanyilmaz.trackcorona.util.DateFormatter;
import com.gokhanyilmaz.trackcorona.service.ClientCountryInfo;
import com.gokhanyilmaz.trackcorona.service.ClientTrackCorona;
import com.gokhanyilmaz.trackcorona.service.IRequest;
import com.gokhanyilmaz.trackcorona.util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private IRequest request;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.request = ClientTrackCorona.getApiClient().create(IRequest.class);
    }

    @Override
    public void mapReady() {
        this.getCountryListWithFlag();
    }

    /**
     * Get Country Name, Country Flag URL, Country Code from API
     */
    @Override
    public void getCountryListWithFlag() {
        IRequest requestCountryInfo = ClientCountryInfo.getApiClient().create(IRequest.class);
        Call<List<ResponseCountryFlag>> call = requestCountryInfo.getCountriesDataWithFlag();
        call.enqueue(new Callback<List<ResponseCountryFlag>>() {
            @Override
            public void onResponse(Call<List<ResponseCountryFlag>> call, Response<List<ResponseCountryFlag>> response) {
                if (response.isSuccessful()) {
                    Log.d(Constant.TAG, "getCountriesDataWithFlag() is success");
                    mapFlagData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCountryFlag>> call, Throwable t) {
                Log.d(Constant.TAG, "getCountryData onFailure" + t.getMessage());
                if (t.getMessage().contains("onFailuretimeout")) getCountryListWithFlag();
            }
        });
    }

    @Override
    public void mapFlagData(List<ResponseCountryFlag> countryFlags) {
        List<AdapterItem> adapterItems = new ArrayList<>();
        for (int i = 0; i < countryFlags.size(); i++) {
            ResponseCountryFlag country = countryFlags.get(i);
            AdapterItem item = new AdapterItem(country.getName(), country.getAlpha2Code(), country.getFlag());
            if (i % 40 == 0) {
                AdapterItem adItem = new AdapterItem();
                adItem.setCountry(false);
                adapterItems.add(adItem);
            }
            adapterItems.add(item);
        }
        view.initCountriesRecycler(adapterItems);
    }

    @Override
    public void countryRowClicked(String countryCode) {
        Call<ResponseCoronaData> call = request.getCoronaData(countryCode);
        call.enqueue(new Callback<ResponseCoronaData>() {
            @Override
            public void onResponse(Call<ResponseCoronaData> call, Response<ResponseCoronaData> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getData().isEmpty()) {
                        Log.d(Constant.TAG, "getCoronaTheCountry() is success");
                        mapCoronaData(response.body().getData());
                    } else view.showErrorMessage("There is no info.");
                }
            }

            @Override
            public void onFailure(Call<ResponseCoronaData> call, Throwable t) {
                Log.d(Constant.TAG, "getCoronaTheCountry() onFailure : " + t.getMessage());
            }
        });
    }

    @Override
    public void getAllCountriesCoronaData() {
        Call<ResponseCoronaData> call = request.getCoronaAllCountries();
        call.enqueue(new Callback<ResponseCoronaData>() {
            @Override
            public void onResponse(Call<ResponseCoronaData> call, Response<ResponseCoronaData> response) {
                if (response.isSuccessful()) {
                    Log.d(Constant.TAG, "getCoronaAllCountries() is success");
                    mapCoronaData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseCoronaData> call, Throwable t) {
                Log.d(Constant.TAG, "getCoronaAllCountries() onFailure : " + t.getMessage());
            }
        });
    }

    @Override
    public void mapCoronaData(List<Country> countryList) {
        List<CountryInfo> countryInfoList = new ArrayList<>();
        for (Country country : countryList) {
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setConfirmed(country.getConfirmed());
            countryInfo.setCountryCode(country.getCountryCode());
            countryInfo.setDead(country.getDead());
            countryInfo.setLatitude(country.getLatitude());
            countryInfo.setLongitude(country.getLongitude());
            countryInfo.setLocation(country.getLocation());
            countryInfo.setRecovered(country.getRecovered());
            countryInfo.setUpdated(country.getUpdated());
            countryInfo.setFormattedDate(new DateFormatter().getFormattedDate(country.getUpdated()));
            countryInfoList.add(countryInfo);
        }
        view.clearHuaweiMap();
        view.bottomSheetSetCollapsed();
        view.setMarker(countryInfoList);
    }
}
