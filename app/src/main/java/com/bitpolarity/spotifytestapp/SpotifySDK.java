package com.bitpolarity.spotifytestapp;


import android.content.Context;
import android.util.Log;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;

public class SpotifySDK {

    // 0F:9B:82:31:61:7F:F9:DA:DC:F9:C5:B8:E1:74:E4:90:4C:85:30:83
    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    ArrayList<String> songData;
    Context context;
    ConnectionParams connectionParams;
    public String trackname;

    SpotifySDK(Context context){
        this.context = context;
    }

  public void onStart() {
        connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Spotify_Handler", "Connected! Yay!");

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Spotify_Handler", throwable.getMessage(), throwable);
                    }
                });
    }

    public void init(){
        connect(connectionParams);
    }



    public void connect(ConnectionParams connectionParams){
        if (connectionParams!= null){
            Log.d("Spotify_Handler", "Spotify hooked , now can do the job");

            connected();
            }
    }

    public void onStop() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }



    public void connected() {
        songData= new ArrayList<String>();

       // mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1EpixuEtZsZg4L");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback( playerState -> {
                    final Track track = playerState.track;


                    if (track != null) {

                        Log.d("MainActivity", track.name + " by " + track.artist.name);

                        Log.d("MainActivity", String.valueOf(track.imageUri));

                        Log.d("MainActivity", track.uri);

                        Log.d("MainActivity", String.valueOf(track.album));

                        Log.d("MainActivity Paused ? ", String.valueOf(playerState.isPaused));

                        Log.d("MainActivity", String.valueOf(playerState.playbackPosition));

                        Log.d("MainActivity", String.valueOf(playerState.playbackOptions));

                        String url = "https://" + "i.scdn.co/image/" + track.imageUri.toString().substring(22, track.imageUri.toString().length() - 2);


                        this.trackname = track.name;

                    }

                });


    }

        }


