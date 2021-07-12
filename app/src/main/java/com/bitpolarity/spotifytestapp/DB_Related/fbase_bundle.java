package com.bitpolarity.spotifytestapp.DB_Related;


public class fbase_bundle {

    public String posterURL, artistName, albumName,trackName,trackLength, trackID;
    public fbase_bundle() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public fbase_bundle(String trackID,String posterURL , String artistName, String albumName, String trackName, String trackLength){
        this.trackID = trackID;
        this.posterURL = posterURL;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.trackLength = trackLength;
    }


}
