package com.bitpolarity.spotifytestapp.Spotify;

public class SongModel {

   public static String imageURI;
   public static String trackName;
   public static String trackArtist;


    public static String getImageURI() {
        return imageURI;
    }

    public static void setImageURI(String imageURI) {
        SongModel.imageURI = imageURI;
        SpotifyViewModel.imageUri.postValue(imageURI);
    }

    public static String getTrackName() {
        return trackName;
    }

    public static void setTrackName(String trackName) {
       //SongModel.trackName = trackName;
       SpotifyViewModel.trackname.postValue(trackName);

    }

    public static String getTrackArtist() {
        return trackArtist;
    }

    public static void setTrackArtist(String trackArtist) {
       SpotifyViewModel.trackArtist.postValue(trackArtist);
    }
}

