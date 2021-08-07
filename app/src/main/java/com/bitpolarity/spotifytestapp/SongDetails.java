package com.bitpolarity.spotifytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SongDetails {

   public static BroadcastReceiver receiver;
    Context context;
    Set<String> details;
    DB_Handler db_handler;
    String requestURL = "https://embed.spotify.com/oembed/?url=";



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
                      String trackId = intent.getStringExtra("id");
                        String artistName = intent.getStringExtra("artist");
                        String albumName = intent.getStringExtra("album");
                        String trackName = intent.getStringExtra("track");
                        String trackLengthInSec = String.valueOf(intent.getIntExtra("length", 0));
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



                            if (brl.length()==0){
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
