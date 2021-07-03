package com.gokhanyilmaz.trackcorona.data;

public class AdapterItem {

    private String countryName;
    private String countryAlphaCode;
    private String countryFlag;
    private boolean isCountry = true;

    public AdapterItem() {
    }

    public AdapterItem(String countryName, String countryAlphaCode, String countryFlag) {
        this.countryName = countryName;
        this.countryAlphaCode = countryAlphaCode;
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryAlphaCode() {
        return countryAlphaCode;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public boolean isCountry() {
        return isCountry;
    }

    public void setCountry(boolean country) {
        isCountry = country;
    }
}
