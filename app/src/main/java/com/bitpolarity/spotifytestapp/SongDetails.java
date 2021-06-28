package com.bitpolarity.spotifytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.IOUtils;

import java.io.BufferedReader;
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

    BroadcastReceiver receiver;
    Context context;
    Set<String> details;
    DB_Handler db_handler;
    String requestURL = "https://embed.spotify.com/oembed/?url=";
    String arl = "";


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
                        String arl = requestURL+trackId;




                        new Thread(new Runnable()
                        {
                            public void run()
                            {

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
                                    //System.out.println(sc.next());
                                }
                                String result = sb.toString();
                                //System.out.println(result);
                                //Removing the HTML tags
                                result = result.replaceAll("<[^>]*>", "");

                                Log.d("urllll", result);
                                String brl = result.split(",")[8].replace("\"thumbnail_url\":","").replace("\"","");
                               // tempDataHolder.setArtUrl(url);

                                Log.d("urlll", brl);
                                String posterUrl = brl;
                                db_handler.setSong_Details(posterUrl,artistName,albumName,trackName, trackLengthInSec);

                                Log.d("SongDetails", "Poster URL: "+posterUrl);
                            }

                        }).start();






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

    String getUrl()
    {
       return arl;
    }
    void setUrl(String url){
        arl = url;
    }




    public List<String> getTextFromWeb(String urlString)
    {
        URLConnection feedUrl;
        List<String> placeAddress = new ArrayList<>();

        try
        {
            feedUrl = new URL(urlString).openConnection();
            InputStream is = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null) // read line by line

            {
                placeAddress.add(line); // add line to list
            }
            is.close(); // close input stream

            return placeAddress; // return whatever you need
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    void readToString(String arl){
        URL url = null;
        try {
            url = new URL(arl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Scanner s = new Scanner(url.openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    String setDetails (String urll){



        //String addressList = readToString(urll);
                   // Log.d("Song Url ",addressList);

                //String url = addressList.get(0).split(",")[8].replace("\"thumbnail_url\":","").replace("\"","");
                //setUrl(url);

        return urll;

    }


}
