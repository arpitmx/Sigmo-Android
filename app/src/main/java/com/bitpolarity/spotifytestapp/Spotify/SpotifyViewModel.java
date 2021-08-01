package com.bitpolarity.spotifytestapp.Spotify;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;


public class SpotifyViewModel extends ViewModel {

    // 0F:9B:82:31:61:7F:F9:DA:DC:F9:C5:B8:E1:74:E4:90:4C:85:30:83

    Application context;

    final String TAG = "SpotifyViewModel";

    public SpotifyViewModel(Application context){
        this.context = context;

    }




}



















