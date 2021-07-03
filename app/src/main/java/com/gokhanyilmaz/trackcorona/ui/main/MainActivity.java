package com.gokhanyilmaz.trackcorona.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gokhanyilmaz.trackcorona.R;
import com.gokhanyilmaz.trackcorona.adapter.CountryAdapter;
import com.gokhanyilmaz.trackcorona.adapter.InfoWindowAdapter;
import com.gokhanyilmaz.trackcorona.data.AdapterItem;
import com.gokhanyilmaz.trackcorona.data.CountryInfo;
import com.gokhanyilmaz.trackcorona.databinding.ActivityMainBinding;
import com.gokhanyilmaz.trackcorona.util.Constant;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMapReadyCallback {

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private HuaweiMap huaweiMap;
    private CountryAdapter adapter;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainPresenter(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constant.MAP_BUNDLE);
        }

        HwAds.init(this);
        this.loadNativeAd(getString(R.string.ad_id_native_small));
        this.initHuaweiMap(mapViewBundle);
        this.bottomSheetSetCollapsed();
    }

    @Override
    public void initHuaweiMap(Bundle bundle) {
        Log.w(Constant.TAG, "initHuaweiMap : ");
        MapsInitializer.setApiKey(Constant.MAP_KEY);
        binding.mapView.onCreate(bundle);
        binding.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.w(Constant.TAG, "onMapReady ");
        this.huaweiMap = huaweiMap;
        presenter.mapReady();
        huaweiMap.setInfoWindowAdapter(new InfoWindowAdapter(this));
    }

    @Override
    public void loadNativeAd(String adId) {
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(this, adId);
        builder.setNativeAdLoadedListener(nativeAd -> {
            this.nativeAd = nativeAd;
            nativeAd.setDislikeAdListener(() -> {
            });
        }).setAdListener(new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {
            }
        });
        NativeAdConfiguration adConfiguration = new NativeAdConfiguration.Builder()
                .setChoicesPosition(NativeAdConfiguration.ChoicesPosition.BOTTOM_LEFT)
                .build();
        NativeAdLoader nativeAdLoader = builder.setNativeAdOptions(adConfiguration).build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());
    }

    @Override
    public void initCountriesRecycler(List<AdapterItem> countryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerCountries.setLayoutManager(layoutManager);
        binding.recyclerCountries.setItemAnimator(new DefaultItemAnimator());

        adapter = new CountryAdapter(this, countryList, nativeAd);
        binding.recyclerCountries.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        this.initClicks();
    }

    @Override
    public void initClicks() {
        adapter.setItemClickListener((view, position, countryCode) -> {
            Log.d(Constant.TAG, "initCountriesRecyclerClicks: " + countryCode);
            presenter.countryRowClicked(countryCode);
        });

        binding.cardViewWorldWide.setOnClickListener(v -> presenter.getAllCountriesCoronaData());
    }

    @Override
    public void clearHuaweiMap() {
        huaweiMap.clear();
    }

    @Override
    public void setMarker(List<CountryInfo> countryInfoList) {
        for (CountryInfo country : countryInfoList) {
            MarkerOptions options = new MarkerOptions()
                    .position(country.getLocationAsLatLng())
                    .title(country.getLocation())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .alpha(0.9f);
            Marker marker = huaweiMap.addMarker(options);
            marker.setTag(country);
            if (countryInfoList.size() == 1) {
                marker.showInfoWindow();
                moveCamera(countryInfoList.get(0).getLocationAsLatLng());
            }
        }
    }

    @Override
    public void moveCamera(LatLng latLng) {
        Log.w(Constant.TAG, "moveCamera ");
        huaweiMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, huaweiMap.getCameraPosition().zoom));
    }

    @Override
    public void bottomSheetSetCollapsed() {
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        binding.mapView.onDestroy();
        if (null != this.nativeAd) this.nativeAd.destroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}