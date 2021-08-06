package com.bitpolarity.spotifytestapp.Spotify;

import static com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository.mSpotifyAppRemote;
import static com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository.track;
import static com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder.playerSeekbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;

import androidx.palette.graphics.Palette;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.ImageUri;

import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
    static TrackProgressBar mTrackProgressBar;
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


       // mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(mPlayerStateEventCallback);

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

//                        mSpotifyAppRemote.getImagesApi().getImage(t_uri).setResultCallback(data -> {
//                            Palette.from(data).maximumColorCount(12).generate(palette -> {
//                                assert palette != null;
//                                Palette.Swatch dominant = palette.getDominantSwatch();
//                               // Palette.Swatch dominant = palette.getDominantSwatch();
//                                if (dominant != null) SongModel.setMpallete(dominant.getRgb());
//                            });
//                        });

                        SongModel.setTrackName(trackName);
                        SongModel.setTrackArtist(trackArtist);
                        SongModel.setImageURI(t_uri);
                        SongModel.setPlayerState(playerState.isPaused);



                       // mTrackProgressBar = new TrackProgressBar(playerSeekbar);


                    }

                });

    }


    public static Bitmap compressImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        int options = 30;
        while ( baos.toByteArray().length / 1024>50) {
            // Clear baos
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10; //
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // the data generated picture ByteArrayInputStream
        return BitmapFactory.decodeStream(isBm, null, null);
    }


    public static final Subscription.EventCallback<PlayerState> mPlayerStateEventCallback =
            playerState -> {
                // Update progressbar
                if (playerState.playbackSpeed > 0) {
                    mTrackProgressBar.unpause();
                } else {
                    mTrackProgressBar.pause();
                }

                // Invalidate seekbar length and position
                playerSeekbar.setMax((int) playerState.track.duration);
                mTrackProgressBar.setDuration(playerState.track.duration);
                mTrackProgressBar.update(playerState.playbackPosition);
            };

}




class TrackProgressBar {

    private static final int LOOP_DURATION = 500;
    private final SeekBar mSeekBar;
    private final Handler mHandler;

    private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    update(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mSpotifyAppRemote
                            .getPlayerApi()
                            .seekTo(seekBar.getProgress());

                }
            };

    private final Runnable mSeekRunnable =
            new Runnable() {
                @Override
                public void run() {
                    int progress = mSeekBar.getProgress();
                    mSeekBar.setProgress(progress + LOOP_DURATION);
                    mHandler.postDelayed(mSeekRunnable, LOOP_DURATION);
                }
            };

    public TrackProgressBar(SeekBar seekBar) {
        mSeekBar = seekBar;
        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBar.setMax((int) track.duration);
        mHandler = new Handler();
    }

    void setDuration(long duration) {
        mSeekBar.setMax((int) duration);
    }

    void update(long progress) {
        mSeekBar.setProgress((int) progress);
    }

    void pause() {
        mHandler.removeCallbacks(mSeekRunnable);
    }

    protected void unpause() {
        mHandler.removeCallbacks(mSeekRunnable);
        mHandler.postDelayed(mSeekRunnable, LOOP_DURATION);
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


