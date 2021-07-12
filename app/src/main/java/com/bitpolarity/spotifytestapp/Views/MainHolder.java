package com.bitpolarity.spotifytestapp.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Profile_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Rooms_Fragment;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Track;


public class MainHolder extends AppCompatActivity {


    BottomNavigationView bottomNavigation; // Bottom navigation bar
    public DrawerLayout drawerLayout;   //Side Navigation bar
    public ActionBarDrawerToggle actionBarDrawerToggle; // Toggle for Side navigation bar

    mDetail_Holder detail_holder;
    String TAG = "MainHolder";
    AudioManager audioManager;
    boolean liked = false;

   // MiniSongPlayerBinding binding;

    ImageButton playback;
    ImageButton side_navigation_button;


    TextView mSongName, mArtistName;
    TextView sigmo_Title;

    ImageView peacock_symbol;
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


        //////////////////////////////////////// Init5ializations ///////////////////////////////////////////////////


      //  binding = DataBindingUtil.setContentView(this ,R.layout.mini_song_player);

       // playback = binding.playback;
        //Models
        detail_holder = new mDetail_Holder();

        //Buttons
        playback = findViewById(R.id.playback);

        side_navigation_button = findViewById(R.id.imageButton);


        //TextViews
        mSongName = findViewById(R.id.m_song_name);
        mArtistName = findViewById(R.id.mArtist_name);
        sigmo_Title = findViewById(R.id.sigmoTitleBar);


        //ImageViews
        cir = findViewById(R.id.cir);
        Fav = findViewById(R.id.add_to_fav);
        peacock_symbol = findViewById(R.id.bitpSymbl);

        //System services
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Intro Animations
        peacock_symbol.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slidein_left_to_right));


        //Object types
        bottomNavigation = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        //////////////////////////////////////// Initializations//////////////////////////////////////////////


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

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Spotify_Handler", "Connected! Yay!");
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Spotify_Handler", throwable.getMessage(), throwable);
                    }
                });



        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        /////////////////////////// OnClick Listeners

        side_navigation_button.setOnClickListener(view -> drawerLayout.open());

        bottomNavigation.setOnItemSelectedListener( item -> {

//           if (item.getItemId()==R.id.nav_home){
//
////                    case R.id.nav_home:
////                        getSupportFragmentManager().beginTransaction()
////                                .replace(R.id.fragmentContainerView,new Home_Fragment()).commit();
////
////                       // Toast.makeText(MainHolder.this, "Home", Toast.LENGTH_SHORT).show();
////                        break;
////                        }


            if (item.getItemId() == R.id.nav_circle){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new Circle_Fragment()).commit();
            //Toast.makeText(MainHolder.this, "Exercise", Toast.LENGTH_SHORT).show();
            }


            else if (item.getItemId() ==  R.id.nav_rooms) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new Rooms_Fragment()).commit();
                //Toast.makeText(MainHolder.this, "Post", Toast.LENGTH_SHORT).show();
            }

            else if (item.getItemId() ==  R.id.nav_profile) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new Profile_Fragment()).commit();
                    //  Toast.makeText(MainHolder.this, "Social", Toast.LENGTH_SHORT).show();
            }


            return true;
        });

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
                       // String url = "https://" + "i.scdn.co/image/" + track.imageUri.toString().substring(22, track.imageUri.toString().length() - 2);
                        mMiniPlayer_Handler(trackName,trackArtist);



                        Fav.setOnClickListener( view -> {
                          {
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
                            Log.d(TAG, "connected: paused ");
                            playback.setImageResource(R.drawable.ic_play);
                            playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));

                            playback.setScaleX(1f);
                            playback.setScaleY(1f);

                        }else{
                            Log.d(TAG, "connected: playing ");

                            playback.setImageResource(R.drawable.ic_baseline_pause_24);
                            playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));

                            playback.setScaleX(1.3f);
                            playback.setScaleY(1.6f);

                        }


                        playback.setOnClickListener(view -> {
                            if(!playerState.isPaused){
                                Log.d(TAG, "connected: playing ");
                                playback.setImageResource(R.drawable.ic_baseline_pause_24);
                                playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in_switch));
                                playback.setScaleX(1.3f);
                                playback.setScaleY(1.6f);
                                mSpotifyAppRemote.getPlayerApi().pause();
                            }
                            else{
                                playback.setImageResource(R.drawable.ic_play);
                                Log.d(TAG, "connected: paused ");

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




    /// Handlers

    public void mMiniPlayer_Handler(String sName, String artistName){
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


    /// Handlers


    @Override
    public void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

}
