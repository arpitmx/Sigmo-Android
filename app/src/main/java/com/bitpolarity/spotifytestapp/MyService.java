package com.bitpolarity.spotifytestapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    BroadcastReceiver receiver;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        IntentFilter filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");

        Toast.makeText(this , "Service Started" , Toast.LENGTH_LONG ).show();


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
                        int trackLengthInSec = intent.getIntExtra("length", 0);

                        Log.d("Broadcast", trackId);
                        Log.d("Broadcast", artistName);
                        Log.d("Broadcast", albumName);
                        Log.d("Broadcast", trackName);
                        Log.d("Broadcast", "Total length :" + trackLengthInSec);




                        Toast.makeText(context, trackName, Toast.LENGTH_LONG).show();


                        break;
                    case broadcaster.BroadcastTypes.PLAYBACK_STATE_CHANGED:
                        boolean playing = intent.getBooleanExtra("playing", false);
                        int positionInMs = intent.getIntExtra("playbackPosition", 0);

                        Log.d("Broadcast", "Playing : " + playing);
                        //t1.setText(""+playing);



                        // Do something with extracted information
                        break;
                    case broadcaster.BroadcastTypes.QUEUE_CHANGED:
                        // Sent only as a notification, your app may want to respond accordingly.
                        break;
                }



            }
        };
        registerReceiver(receiver, filter);



        return START_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
