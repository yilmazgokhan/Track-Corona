package com.gokhanyilmaz.trackcorona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.gokhanyilmaz.trackcorona.data.CountryInfo;
import com.gokhanyilmaz.trackcorona.databinding.CustomMarkerInfoBinding;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Marker;

public class InfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        CustomMarkerInfoBinding binding = CustomMarkerInfoBinding.inflate(LayoutInflater.from(context));
        CountryInfo country = (CountryInfo) marker.getTag();
        binding.title.setText(marker.getTitle());
        binding.deaths.setText(String.format("Deaths: %s", country.getDead()));
        binding.patients.setText(String.format("Patients: %s", country.getConfirmed()));
        binding.recovered.setText(String.format("Recovered Patients: %s", country.getRecovered()));
        binding.updated.setText(String.format("Updated: %s", country.getFormattedDate()));
        return binding.getRoot();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
