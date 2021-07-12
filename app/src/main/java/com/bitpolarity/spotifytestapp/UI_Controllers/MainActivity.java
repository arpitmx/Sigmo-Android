package com.bitpolarity.spotifytestapp.UI_Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.broadcaster;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    SharedPreferences prefs;
    AudioManager mAudio;
    int trackLengthInSec = 0;

    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);



//        Intent i= new Intent(MainActivity.this, MyService.class);
//        i.putExtra("KEY1", "Value to be used by the service");
//        this.startService(i);

        t1 = findViewById(R.id.s1);
        t2 = findViewById(R.id.s2);
        t3 = findViewById(R.id.s3);
        t4 = findViewById(R.id.s4);
        t5 = findViewById(R.id.s5);
        t6 = findViewById(R.id.s6);
        t7 = findViewById(R.id.s7);
        t8 = findViewById(R.id.s8);
        prefs = getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);

        Toast.makeText(MainActivity.this , "Welcome, "+prefs.getString("Username", "Error No user found, clear cache!") ,Toast.LENGTH_LONG).show();



//        Intent intent = new Intent();
//        intent.setAction("com.spotify.music.playbackstatechanged"); sendBroadcast(intent);




        //do something based on the intent's action

    }

    @Override
   public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");



        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ArrayList<String> songs = new ArrayList<>();

                long timeSentInMs = intent.getLongExtra("timeSent", 0L);

                String action = intent.getAction();

                switch (action) {
                    case broadcaster.BroadcastTypes.METADATA_CHANGED:


                        String trackId = intent.getStringExtra("id");
                        String artistName = intent.getStringExtra("artist");
                        String albumName = intent.getStringExtra("album");
                        String trackName = intent.getStringExtra("track");
                        trackLengthInSec = intent.getIntExtra("length", 0);
                        Long timeSent = intent.getLongExtra("timeSent",0);

                        songs.add(trackName);


                        Log.d("Broadcast", trackId);
                        Log.d("Broadcast", artistName);
                        Log.d("Broadcast", albumName);
                        Log.d("Broadcast", trackName);
                        Log.d("Broadcast", "Total length :" + trackLengthInSec);

                        t2.setText("TrackName :"+trackName);
                        t3.setText("Album :"+albumName);
                        t4.setText("Total Time: "+(trackLengthInSec));
                        t5.setText("Track ID : "+trackId);
                        t6.setText("Artist Name:"+artistName);
                        t8.setText("Time sent :"+timeSent);



                      //  Toast.makeText(context, trackName, Toast.LENGTH_LONG).show();


                        break;
                    case broadcaster.BroadcastTypes.PLAYBACK_STATE_CHANGED:

//
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        Log.d("run", "run: "+positionInMs);
//                                    }
//                                });
//                            }
//                        },100);


                        boolean playing = intent.getBooleanExtra("playing", false);
                        Log.d("Broadcast", "Playing : " + playing);
                        t1.setText(""+playing);

                        int positionInMs = intent.getIntExtra("playbackPosition", 0);
                    t7.setText("Position :"+positionInMs);

                        // Do something with extracted information
                        break;
                    case broadcaster.BroadcastTypes.QUEUE_CHANGED:
                        // Sent only as a notification, your app may want to respond accordingly.
                        break;
                }



            }
        };
        registerReceiver(receiver, filter);

    }

    @Override
  public void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }


    void mute(ArrayList<String> a){
        int size = a.size();
        if (a.get(size - 1).equals(a.get(size - 2))){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAudio.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
                Log.d("Main", "muted ");
            }
        }

    }
}