package com.bitpolarity.spotifytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

public class SongDetails {

   public static BroadcastReceiver receiver;
    Context context;
    DB_Handler db_handler;
    String requestURL = "https://embed.spotify.com/oembed/?url=";
    private static String error1 = "Null";
    String trackId,artistName,albumName,trackName,trackLengthInSec;




    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

    public void init_br(String username){
        db_handler = new DB_Handler();
        db_handler.setUsername(username);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");



        receiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();



                switch (action) {



                    case broadcaster.BroadcastTypes.METADATA_CHANGED:

                       trackId = intent.getStringExtra("id");
                        artistName = intent.getStringExtra("artist");
                        albumName = intent.getStringExtra("album");
                        trackName = intent.getStringExtra("track");
                        trackLengthInSec = String.valueOf(intent.getIntExtra("length", 0));

                        if(trackId==null && artistName == null && albumName == null && trackName == null && trackLengthInSec == null){
                            trackId = error1;
                            albumName = error1;
                            trackName = error1;
                            trackLengthInSec = error1;
                        }

                        String arl = requestURL+trackId;

                        new Thread(() -> {

                            URL url = null;
                            try {
                                url = new URL(arl);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                            Scanner sc = null;

                            try {
                                sc = new Scanner(url.openStream());
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                            StringBuffer sb = new StringBuffer();
                            while(sc.hasNext()) {
                                sb.append(sc.next());
                            }


                            String result = sb.toString();

                            result = result.replaceAll("<[^>]*>", "");

                            Log.d("urllll", result);
                            String brl = result.split(",")[8].replace("\"thumbnail_url\":","").replace("\"","");

                            if (brl == null){
                                brl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQpnVsnrM_RRT2ty6uAXPwoQMQIIQNb7V8cQ&usqp=CAU";
                            }

                            db_handler.setSong_Details(trackId,brl,artistName,albumName,trackName, trackLengthInSec);
                            Log.d("SongDetails", "Poster URL: "+brl);
                        }).start();


                        Log.d("Broadcast", trackId);
                        Log.d("Broadcast", artistName);
                        Log.d("Broadcast", albumName);
                        Log.d("Broadcast", trackName);
                        Log.d("Broadcast", "Total length :" + trackLengthInSec);
                      //  Toast.makeText(context, trackName, Toast.LENGTH_SHORT).show();

                        break;


                    case broadcaster.BroadcastTypes.PLAYBACK_STATE_CHANGED:

                        boolean playing = intent.getBooleanExtra("playing", false);
                        //Toast.makeText(context, ""+playing,Toast.LENGTH_SHORT).show();

                        db_handler.setSong_PlaybackDetails(playing);
                        Log.d("Broadcast", "Playing : " + playing);



                        break;


                    case broadcaster.BroadcastTypes.QUEUE_CHANGED:
                        // Sent only as a notification, your app may want to respond accordingly.
                        break;
                }



            }
        };


        context.registerReceiver(receiver, filter);


    }



    public void setContext(Context context){
        this.context= context;
    }









}
