package com.bitpolarity.spotifytestapp.DB_Related;


public class TempDataHolder {

    String[] songDetails;
    String[] trackID;
    static String sName;
   String artist;


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

    public static String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getArtist() {
        return artist;
    }

    public  void setArtist(String artist) {
        this.artist = artist;
    }


    public TempDataHolder(String sname){
        sName = sname;
    }
    public TempDataHolder(){

    }


}