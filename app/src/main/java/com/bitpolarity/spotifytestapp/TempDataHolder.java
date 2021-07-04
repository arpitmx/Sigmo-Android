package com.bitpolarity.spotifytestapp;

import android.app.Activity;
import android.util.Log;

public class TempDataHolder {

    String[] songDetails;

    public String[] getSongDetails() {
        return songDetails;
    }

    public void setSongDetails(String[] songDetails) {
        this.songDetails = songDetails;
    }
}