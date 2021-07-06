package com.bitpolarity.spotifytestapp.database_related;

import android.app.Activity;
import android.util.Log;

public class TempDataHolder {

    String[] songDetails;
    String[] trackID;

    public String[] getSongDetails() {
        return songDetails;
    }

    public void setSongDetails(String[] songDetails) {
        this.songDetails = songDetails;
    }

    public String[] getTrackID() {
        return trackID;
    }

    public void setTrackID(String[] trackID) {
        this.trackID = trackID;
    }
}