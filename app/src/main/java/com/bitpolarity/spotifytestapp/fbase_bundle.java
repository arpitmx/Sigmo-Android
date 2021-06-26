package com.bitpolarity.spotifytestapp;


public class fbase_bundle {

    public String  trackID , artistName, albumName,trackName,trackLength;
    public fbase_bundle() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public fbase_bundle(String trackID , String artistName, String albumName, String trackName, String trackLength){
        this.trackID = trackID;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.trackLength = trackLength;
    }


}
