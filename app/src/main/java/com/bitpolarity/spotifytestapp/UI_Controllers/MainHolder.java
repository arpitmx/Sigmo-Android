package com.bitpolarity.spotifytestapp.UI_Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle.StatusActivity;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Profile_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms_Fragment;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;

import com.bitpolarity.spotifytestapp.ViewModels.Spotify_ViewModel;
import com.bitpolarity.spotifytestapp.databinding.ActivityMainHolderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.Track;


public class MainHolder extends AppCompatActivity {


    BottomNavigationView bottomNavigation; // Bottom navigation bar
    public DrawerLayout drawerLayout;   //Side Navigation bar
    public ActionBarDrawerToggle actionBarDrawerToggle; // Toggle for Side navigation bar

    mDetail_Holder detail_holder;
    String TAG = "MainHolder";
    AudioManager audioManager;
    boolean liked = false;
    float prevVolume;


   //ViewBinidings
    ActivityMainHolderBinding binding;
    Spotify_ViewModel spotify_viewModel;


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

        binding = ActivityMainHolderBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        //////////////////////////////////////// Init5ializations ///////////////////////////////////////////////////

        //ViewBindings
        //miniSongPlayerBinding = MiniSongPlayerBinding.inflate(getLayoutInflater());


        //ViewModel
        //spotify_viewModel = ViewModelProviders.of(this).get(Spotify_ViewModel.class);

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

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Spotify_Handler", "Connected! Yay!");
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Spotify_Handler", throwable.getMessage(), throwable);
                    }
                });



        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
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

                if (binding.customAction.sigmoTitleBar.getVisibility()==View.GONE){
                binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
                binding.customAction.sigmoTitleBar.setVisibility(View.VISIBLE);
                    binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left_to_right_fr));
                    binding.customAction.bitpSymbl.setVisibility(View.VISIBLE);
                    binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                    binding.customAction.Rooms.setVisibility(View.GONE);

                }

            }


            else if (item.getItemId() ==  R.id.nav_rooms) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new Rooms_Fragment()).commit();
               binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                binding.customAction.sigmoTitleBar.setVisibility(View.GONE);
                binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left_fr));
                binding.customAction.bitpSymbl.setVisibility(View.GONE);

                binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
                binding.customAction.Rooms.setVisibility(View.VISIBLE);
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


   //   TempDataHolder mDetail_holder = new TempDataHolder();
//
        // Subscribe to PlayerState

      mSpotifyAppRemote.getPlayerApi().resume();

        mSpotifyAppRemote.getPlayerApi()

                .subscribeToPlayerState()
                .setEventCallback( playerState -> {
                    final Track track = playerState.track;

                    if (track != null) {
                       // int vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                       // Log.d(TAG, "connected: volume "+vol);
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


                        //float volu = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        //Toast.makeText(this, "Previous volume : " + volu, Toast.LENGTH_SHORT).show();

                        if(trackName.equals("Advertisement")){

                            //this.prevVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            Toast.makeText(this , "Muting ads",Toast.LENGTH_SHORT   ).show();
                            mSpotifyAppRemote.getConnectApi().connectSetVolume(0f);

                        }
                        }


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

