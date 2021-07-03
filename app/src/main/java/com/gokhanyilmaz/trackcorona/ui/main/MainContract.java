package com.gokhanyilmaz.trackcorona.ui.main;

import android.os.Bundle;

import com.gokhanyilmaz.trackcorona.data.AdapterItem;
import com.gokhanyilmaz.trackcorona.data.CountryInfo;
import com.gokhanyilmaz.trackcorona.data.model.Country;
import com.gokhanyilmaz.trackcorona.data.model.ResponseCountryFlag;
import com.huawei.hms.maps.model.LatLng;

import java.util.List;

public interface MainContract {

    interface View {

        void initHuaweiMap(Bundle bundle);

        void loadNativeAd(String adId);

        void initCountriesRecycler(List<AdapterItem> items);

        void initClicks();

        void clearHuaweiMap();

        void setMarker(List<CountryInfo> countryInfoList);

        void moveCamera(LatLng latLng);

        void bottomSheetSetCollapsed();

        void showErrorMessage(String message);
    }

    interface Presenter {

        void mapReady();

        void getCountryListWithFlag();

        void mapFlagData(List<ResponseCountryFlag> countryFlags);

        void getAllCountriesCoronaData();

        void countryRowClicked(String countryCode);

        void mapCoronaData(List<Country> countryList);

    }
}
