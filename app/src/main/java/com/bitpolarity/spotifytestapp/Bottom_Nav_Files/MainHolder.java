package com.bitpolarity.spotifytestapp.Bottom_Nav_Files;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import androidx.annotation.NonNull;

import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle.Circle_Fragment;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;

import com.bitpolarity.spotifytestapp.database_related.TempDataHolder;
import com.bitpolarity.spotifytestapp.mMiniPlayer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Track;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainHolder extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    mMiniPlayer miniPlayer;
    TextView sigmoTV ;
    ImageView bitpSymbol;
    ImageButton imgbtn;
    CircleImageView c;
    ImageButton playback;
    mDetail_Holder detail_holder;
    TextView mSongName, mArtistName;
    String TAG = "MainHolder";
    TempDataHolder instance;

    boolean liked = false;


    ImageView cir;
    ImageView Fav;

    /////////////////////// SPOTIFY

    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;


    ////////////////////////////////




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_holder);


        playback = findViewById(R.id.playback);


        imgbtn = findViewById(R.id.imageButton);
        sigmoTV = findViewById(R.id.sigmoTitleBar);
        bitpSymbol = findViewById(R.id.bitpSymbl);
        playback = findViewById(R.id.playback);
        detail_holder = new mDetail_Holder();

        mSongName = findViewById(R.id.m_song_name);
        mArtistName = findViewById(R.id.mArtist_name);
        cir = findViewById(R.id.cir);
        Fav = findViewById(R.id.add_to_fav);


        bitpSymbol.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        sigmoTV.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        sigmoTV.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slidein_left_to_right));

        bottomNavigation = findViewById(R.id.bottom_nav);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

    }

    @Override
    public void onStart() {

        super.onStart();


        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(MainHolder.this, connectionParams,
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









        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();

            }
        });


        miniPlayer = new mMiniPlayer(this);
        // miniPlayer.show(getSupportFragmentManager(),"Mini Player");


        bottomNavigation.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragmentContainerView,new Home_Fragment()).commit();
//
//                       // Toast.makeText(MainHolder.this, "Home", Toast.LENGTH_SHORT).show();
//                        break;


                case R.id.nav_circle:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new Circle_Fragment()).commit();
                    //Toast.makeText(MainHolder.this, "Exercise", Toast.LENGTH_SHORT).show();
                    break;


                case R.id.nav_rooms:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new Rooms_Fragment()).commit();
                    //Toast.makeText(MainHolder.this, "Post", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.nav_profile:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new Profile_Fragment()).commit();

                    //  Toast.makeText(MainHolder.this, "Social", Toast.LENGTH_SHORT).show();
                    break;


            }
            return true;


        });

    }



    public void mMiniPlayer_Handler(String sName,String artistName){
        Log.d(TAG, "mMiniPlayer_Handler: "+ sName);
        mSongName.setText(sName);
        mArtistName.setText(artistName);


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
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

                        mMiniPlayer_Handler(trackName,trackArtist);


                        Log.d(TAG, "connected: "+mSpotifyAppRemote.getUserApi().getLibraryState(track.uri).getRequestId());


                        Fav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!liked) {
                                    mSpotifyAppRemote.getUserApi().addToLibrary(track.uri);
                                    Fav.setImageResource(R.drawable.ic_hfilled);
                                    liked = true;
                                }
                                else{
                                    mSpotifyAppRemote.getUserApi().removeFromLibrary(track.uri);
                                    Fav.setImageResource(R.drawable.ic_fav);
                                    liked = false;
                                }

                            }
                        });


                        if(playerState.isPaused){
                            playback.setImageResource(R.drawable.ic_play);
                            playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));

                            playback.setScaleX(1f);
                            playback.setScaleY(1f);

                        }else{
                            playback.setImageResource(R.drawable.ic_baseline_pause_24);
                            playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));

                            playback.setScaleX(1.3f);
                            playback.setScaleY(1.6f);

                        }


                        playback.setOnClickListener(view -> {
                            if(!playerState.isPaused){
                                playback.setImageResource(R.drawable.ic_baseline_pause_24);
                                playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));
                                playback.setScaleX(1.3f);
                                playback.setScaleY(1.6f);
                                mSpotifyAppRemote.getPlayerApi().pause();
                            }
                            else{
                                playback.setImageResource(R.drawable.ic_play);
                                playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));

                                playback.setScaleX(1f);
                                playback.setScaleY(1f);
                                mSpotifyAppRemote.getPlayerApi().resume();

                            }
                        });


                        mSpotifyAppRemote.getImagesApi().getImage(track.imageUri).setResultCallback(new CallResult.ResultCallback<Bitmap>() {
                            @Override public void onResult(Bitmap bitmap)
                            {
                                cir.setImageBitmap(bitmap);
                            } });



                    }

                });

    }
}

