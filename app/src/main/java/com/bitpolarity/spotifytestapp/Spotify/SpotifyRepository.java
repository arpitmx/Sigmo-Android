package com.bitpolarity.spotifytestapp.Spotify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.palette.graphics.Palette;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SongDetails;
import com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

public class SpotifyRepository {

    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    public static SpotifyAppRemote mSpotifyAppRemote;
    Context context;
    SharedPreferences prefs;
    final String TAG = "SpotifySDK";
    boolean connected ;
    boolean liked = false;
    String trackUri;
    public static Track track;



     public SpotifyRepository(Context context){
        this.context = context;
        connected = false;
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
                        connected();
                        connected = true;
                        // Now you can start interacting with App Remote
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



        mSpotifyAppRemote.getPlayerApi().resume();

      mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(playerState -> {
         Track brack = playerState.track;
          if (brack != null) {
              SongModel.setPlayerState(playerState.isPaused);
          }
      });


        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback( playerState -> {
                    track = playerState.track;
                    if (track != null) {

                        ImageUri t_uri = track.imageUri;
                        String trackName = track.name;
                        String trackArtist = String.valueOf(track.artist.name);
                        this.trackUri = track.uri;
                        Log.d(TAG, "connected: trackname "+trackName);
                        Log.d(TAG, "connected: trackURI "+t_uri);

                        mSpotifyAppRemote.getImagesApi().getImage(t_uri).setResultCallback(data -> {
                            Palette.from(data).maximumColorCount(12).generate(palette -> {
                                assert palette != null;
                                Palette.Swatch dominant = palette.getVibrantSwatch();
                                //Palette.Swatch dominant = palette.getDominantSwatch();
                               // if (dominant != null) SongModel.setMpallete(dominant.getRgb());
                            });
                        });

                        SongModel.setTrackName(trackName);
                        SongModel.setTrackArtist(trackArtist);
                        SongModel.setImageURI(t_uri);

                    }

                });

    }




}























//                        Log.d("MainActivity", trackName + " by " + trackArtist);
//                        Log.d("MainActivity", String.valueOf(track.imageUri));
//                        Log.d("MainActivity", track.uri);
//                        Log.d("MainActivity", String.valueOf(track.album));
//                        Log.d("MainActivity Paused ? ", String.valueOf(playerState.isPaused));
//                        Log.d("MainActivity", String.valueOf(playerState.playbackPosition));
//                        Log.d("MainActivity", String.valueOf(playerState.playbackOptions));
//                        String url = "https://" + "i.scdn.co/image/" + track.imageUri.toString().substring(22, track.imageUri.toString().length() - 2);


