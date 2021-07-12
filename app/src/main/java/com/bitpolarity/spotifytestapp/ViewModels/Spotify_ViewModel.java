package com.bitpolarity.spotifytestapp.ViewModels;
import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.DB_Related.TempDataHolder;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;


public class Spotify_ViewModel extends ViewModel {

    // 0F:9B:82:31:61:7F:F9:DA:DC:F9:C5:B8:E1:74:E4:90:4C:85:30:83
    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    Context context;
    SharedPreferences prefs;

   public Boolean mIsPaused;
   public  String mTrackUri;
   public  String mImageUri;

    public Spotify_ViewModel(Context context){
        this.context = context;
        onStart();

    }


     public void onStart() {


        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Spotify_Handler", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Spotify_Handler", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }


    public void onStop() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    public void connected() {

       //TempDataHolder mDetail_holder = new TempDataHolder();

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()

                .subscribeToPlayerState()
                .setEventCallback( playerState -> {
                    final Track track = playerState.track;

                    if (track != null) {

                        String trackName = track.name;
                        String trackArtist = String.valueOf(track.artist.name);

                        Log.d("MainActivity", trackName + " by " + trackArtist);
                        Log.d("MainActivity", String.valueOf(track.imageUri));
                        Log.d("MainActivity", track.uri);
                        Log.d("MainActivity", String.valueOf(track.album));
                        Log.d("MainActivity Paused ? ", String.valueOf(playerState.isPaused));
                        Log.d("MainActivity", String.valueOf(playerState.playbackPosition));
                        Log.d("MainActivity", String.valueOf(playerState.playbackOptions));
                        String url = "https://" + "i.scdn.co/image/" + track.imageUri.toString().substring(22, track.imageUri.toString().length() - 2);



//                        mDetail_Holder appDetails = mDetail_Holder.getInstance();
//                        appDetails.setSong_Title(trackName);


                    }

                });

    }
}
