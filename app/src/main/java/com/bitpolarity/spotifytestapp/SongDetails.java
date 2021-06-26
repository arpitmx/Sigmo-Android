package com.bitpolarity.spotifytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SongDetails {

    BroadcastReceiver receiver;
    Context context;
    Set<String> details;
    DB_Handler db_handler;


    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

    void init_br(String username){
        db_handler = new DB_Handler();
        db_handler.setUsername(username);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                long timeSentInMs = intent.getLongExtra("timeSent", 0L);

                String action = intent.getAction();

                switch (action) {
                    case broadcaster.BroadcastTypes.METADATA_CHANGED:
                        String trackId = intent.getStringExtra("id");
                        String artistName = intent.getStringExtra("artist");
                        String albumName = intent.getStringExtra("album");
                        String trackName = intent.getStringExtra("track");
                        String trackLengthInSec = String.valueOf(intent.getIntExtra("length", 0));

                        db_handler.setSong_Details(trackId,artistName,albumName,trackName, trackLengthInSec);

                        Log.d("Broadcast", trackId);
                        Log.d("Broadcast", artistName);
                        Log.d("Broadcast", albumName);
                        Log.d("Broadcast", trackName);
                        Log.d("Broadcast", "Total length :" + trackLengthInSec);
                        Toast.makeText(context, trackName, Toast.LENGTH_SHORT).show();

                        break;
                    case broadcaster.BroadcastTypes.PLAYBACK_STATE_CHANGED:
                        boolean playing = intent.getBooleanExtra("playing", false);
                        int positionInMs = intent.getIntExtra("playbackPosition", 0);

                        Log.d("Broadcast", "Playing : " + playing);

                        // Do something with extracted information
                        break;
                    case broadcaster.BroadcastTypes.QUEUE_CHANGED:
                        // Sent only as a notification, your app may want to respond accordingly.
                        break;
                }



            }
        };
        context.registerReceiver(receiver, filter);

    }


    void setContext(Context context){
        this.context= context;
    }



//    String[] setSong_Details(String trackID , String artistName, String albumName, String trackName, String trackLength ){
//
//
//    }




}
