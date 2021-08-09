package com.bitpolarity.spotifytestapp.Services;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.SongDetails;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModel;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModelFactory;
import com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder;

public class OnClearFromRecentService extends Service {

    DB_Handler dbHolder = new DB_Handler();
    SharedPreferences prefs;
    String USERNAME;
    String LOG = "SERVICE";
    SpotifyRepository spotifyRepository;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
        USERNAME = prefs.getString("Username","Error-1");

        SongDetails songDetails = new SongDetails();
        spotifyRepository = new SpotifyRepository(getApplicationContext());

        songDetails.setContext(getBaseContext());
        songDetails.init_br(USERNAME);

        spotifyRepository.onStart();


        dbHolder = new DB_Handler();
        dbHolder.setUsername(USERNAME);

        Log.d(LOG, "Service Started");
        dbHolder.setStatus(1);

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(SongDetails.receiver);
        super.onDestroy();
        Log.d(LOG, "Service Destroyed");


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //TODO BUG1 : ONLINE STATUS NOT CHANGING IN SOME MODELS

        Log.e(LOG, "App closed completly!");
        spotifyRepository.onStop();
        dbHolder.setStatus(0);
        Toast.makeText(getBaseContext(), "Sigmo Closed forcefly", Toast.LENGTH_SHORT).show();
        stopSelf();
    }


}