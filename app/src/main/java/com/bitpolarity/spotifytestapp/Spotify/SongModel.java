package com.bitpolarity.spotifytestapp.Spotify;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.spotify.protocol.types.ImageUri;

public class SongModel {

    private static MutableLiveData<String> tNAME ;
    private static MutableLiveData<String> tArtist;
    private static MutableLiveData<ImageUri> tImgURI;
    private static MutableLiveData<Boolean> playerState;

    public static void setTrackName(String trackName) {
        tNAME.postValue(trackName);
    }

    public static void setTrackArtist(String trackArtist){
        tArtist.postValue(trackArtist);
    }

    public static void setImageURI(ImageUri uri){
        tImgURI.postValue(uri);
    }

    public static void setPlayerState(Boolean playerStateg){
        playerState.postValue(playerStateg);
    }


    /////////////////////////////////////////////////////////////////////////////////////////

    public static LiveData<String> getTrackName(){
        if (tNAME == null)tNAME = new MutableLiveData<>();
        return tNAME;

    }

    public static LiveData<String> getTrackArtist(){
        if(tArtist == null) tArtist = new MutableLiveData<>();
        return tArtist;
    }


    public static LiveData<ImageUri> getImgURI(){
        if(tImgURI == null) tImgURI = new MutableLiveData<>();
        return tImgURI;
    }

    public static LiveData<Boolean> getPlayerState(){
        if(playerState == null) playerState = new MutableLiveData<>();
        return playerState;
    }




}














