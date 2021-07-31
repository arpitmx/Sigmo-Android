package com.bitpolarity.spotifytestapp.Spotify;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;


public class SpotifyViewModel extends ViewModel {

    // 0F:9B:82:31:61:7F:F9:DA:DC:F9:C5:B8:E1:74:E4:90:4C:85:30:83

    public static MutableLiveData<String> trackname ;
    public static MutableLiveData<String> imageUri ;
    public static MutableLiveData<String> trackArtist ;
    Application context;

    public SpotifyViewModel(Application context){
        this.context = context;
        trackname = new MutableLiveData<>();
        imageUri  = new MutableLiveData<>();
        trackArtist = new MutableLiveData<>();
    }

    public LiveData<String> getTrackName(){
        return trackname;
    }

    public LiveData<String> getTrackArtist(){
        return trackArtist;
    }
    public LiveData<String> getImageURI(){
        return imageUri;
    }


}
