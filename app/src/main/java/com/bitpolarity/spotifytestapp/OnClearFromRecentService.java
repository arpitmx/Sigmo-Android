package com.bitpolarity.spotifytestapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.SpotifyHandler.Spotify_Handler;

import java.util.function.ToDoubleBiFunction;

public class OnClearFromRecentService extends Service {

    DB_Handler dbHolder = new DB_Handler();
    SharedPreferences prefs;
    String USERNAME;
    String LOG = "SERVICE";
    Spotify_Handler spotify_handler;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
        USERNAME = prefs.getString("Username","Error-1");
        SongDetails songDetails = new SongDetails();
        songDetails.setContext(getBaseContext());
        songDetails.init_br(USERNAME);


        dbHolder = new DB_Handler();

        dbHolder.setUsername(USERNAME);

        Log.d(LOG, "Service Started");
        dbHolder.setStatus(1);
        //dbHolder.fetchSong_Details_From_DB();

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "Service Destroyed");
        spotify_handler.onStop();

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //TODO BUG1 : ONLINE STATUS NOT CHANGING IN SOME MODELS

        Log.e(LOG, "App closed completly!");
        dbHolder.setStatus(0);
        Toast.makeText(getBaseContext(), "Sigmo Closed forcefly", Toast.LENGTH_LONG).show();
        stopSelf();
    }
}