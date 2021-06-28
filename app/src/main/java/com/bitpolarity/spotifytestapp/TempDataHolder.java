package com.bitpolarity.spotifytestapp;

import android.util.Log;

public class TempDataHolder {

    String artUrl;

    public String getArtUrl() {
        Log.d("Temp", "getArtUrl: "+artUrl);
        return this.artUrl;



    }

    public void setArtUrl(String artUrl) {

        this.artUrl = artUrl;
        Log.d("Temp", "setArtUrl: "+artUrl);
    }
}
