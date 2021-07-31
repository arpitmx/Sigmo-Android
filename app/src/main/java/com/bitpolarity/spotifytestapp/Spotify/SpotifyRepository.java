package com.bitpolarity.spotifytestapp.Spotify;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

public class SpotifyRepository {

    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    Context context;
    SharedPreferences prefs;
    final String TAG = "SpotifySDK";
    boolean connected ;
    boolean liked = false;

    String trackUri;

    ImageView Fav;


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

//    void connect(){
//
//        if (connected){
//            connected();
//        }else{
//            Log.d(TAG, "connect: Spotify not connected");
//        }
//
//    }


    public void onStop() {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    public void connected() {


        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback( playerState -> {
                    final Track track = playerState.track;

                    if (track != null) {

                        String t_uri = String.valueOf(track.imageUri);

                        String trackName = track.name;
                        String trackArtist = String.valueOf(track.artist.name);
                        String url = "https://" + "i.scdn.co/image/" + t_uri.substring(22, t_uri.length() - 2);

                        this.trackUri = track.uri;



                        Log.d(TAG, "connected: trackname "+trackName);

                        SongModel.setImageURI(url);
                        SongModel.setTrackName(trackName);
                        SongModel.setTrackArtist(trackArtist);

                    }

                });

    }


    public void fav_clicked(){


            {
                if (!liked) {
                    mSpotifyAppRemote.getUserApi().addToLibrary(trackUri);
                    Fav.setImageResource(R.drawable.ic_heart);
                    Fav.setAnimation(AnimationUtils.loadAnimation(context, R.anim.pop_in));
                    liked = true;
                }
                else{
                    mSpotifyAppRemote.getUserApi().removeFromLibrary(trackUri);
                    Fav.setImageResource(R.drawable.ic_fav);
                    Fav.setAnimation(AnimationUtils.loadAnimation(context, R.anim.pop_out));

                    liked = false;
                }

            }

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


