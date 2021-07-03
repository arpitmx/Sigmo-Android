package com.bitpolarity.spotifytestapp;

import android.app.Activity;
import android.util.Log;

public class TempDataHolder {

    String artUrl;
    Activity context;

    TempDataHolder(Activity context){
        this.context = context;

    }

    Activity getContext(){
        return this.context;
    }

    public String getArtUrl() {
        Log.d("Temp", "getArtUrl: "+artUrl);
        return this.artUrl;

    }

    public void setArtUrl(String artUrl) {

        this.artUrl = artUrl;
        Log.d("Temp", "setArtUrl: "+artUrl);
    }
}
