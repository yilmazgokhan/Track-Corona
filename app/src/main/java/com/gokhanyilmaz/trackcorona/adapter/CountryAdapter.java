package com.gokhanyilmaz.trackcorona.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.gokhanyilmaz.trackcorona.R;
import com.gokhanyilmaz.trackcorona.data.AdapterItem;
import com.gokhanyilmaz.trackcorona.databinding.NativeSmallAdBinding;
import com.gokhanyilmaz.trackcorona.databinding.RowCountryBinding;
import com.gokhanyilmaz.trackcorona.util.Constant;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AdapterItem> countries;
    private NativeAd nativeAd;
    private ItemClickListener itemClickListener;

    public CountryAdapter(Context context, List<AdapterItem> countries, NativeAd nativeAd) {
        this.context = context;
        this.countries = countries;
        this.nativeAd = nativeAd;
    }

    @Override
    public int getItemViewType(int position) {
        if (countries.get(position).isCountry())
            return Constant.MENU_ITEM_VIEW_TYPE;
        return Constant.UNIFIED_NATIVE_AD_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constant.UNIFIED_NATIVE_AD_VIEW_TYPE:
                NativeSmallAdBinding bindingAd = NativeSmallAdBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new AdViewHolder(bindingAd);
            case Constant.MENU_ITEM_VIEW_TYPE:
                //go to default
            default:
                RowCountryBinding bindingCountry = RowCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(bindingCountry);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case Constant.MENU_ITEM_VIEW_TYPE:
                AdapterItem currentItem = getItem(position);
                if (currentItem != null) {
                    ((ViewHolder) holder).binding.rowCountryText.setText(currentItem.getCountryName());
                    GlideToVectorYou
                            .init()
                            .with(context)
                            .load(Uri.parse(currentItem.getCountryFlag()), ((ViewHolder) holder).binding.rowCountryImage);
                }
                break;
            case Constant.UNIFIED_NATIVE_AD_VIEW_TYPE:
                NativeView nativeView = ((AdViewHolder) holder).binding.nativeSmallView;
                // Register a native ad material view.
                nativeView.setTitleView(nativeView.findViewById(R.id.ad_title));
                nativeView.setMediaView((MediaView) nativeView.findViewById(R.id.ad_media));
                nativeView.setAdSourceView(nativeView.findViewById(R.id.ad_source));
                nativeView.setCallToActionView(nativeView.findViewById(R.id.ad_call_to_action));

                // Populate the native ad material view. The native ad must contain the title and media materials.
                ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
                nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());

                if (null != nativeAd.getAdSource()) {
                    ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
                }
                nativeView.getAdSourceView().setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);

                if (null != nativeAd.getCallToAction()) {
                    ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
                }
                nativeView.getCallToActionView().setVisibility(null != nativeAd.getCallToAction() ? View.VISIBLE : View.INVISIBLE);

                // Register a native ad object.
                nativeView.setNativeAd(nativeAd);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public AdapterItem getItem(int position) {
        return countries.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public String getCountryCode(int position) {
        return countries.get(position).getCountryAlphaCode();
    }

    public interface ItemClickListener {

        void onClick(View view, int position, String countryCode);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RowCountryBinding binding;

        public ViewHolder(RowCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onClick(v, getAdapterPosition(), getCountryCode(getAdapterPosition()));
        }
    }

    class AdViewHolder extends RecyclerView.ViewHolder {

        private NativeSmallAdBinding binding;

        public AdViewHolder(NativeSmallAdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}