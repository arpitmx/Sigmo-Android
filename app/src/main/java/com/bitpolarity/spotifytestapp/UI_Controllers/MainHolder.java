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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModelFactory;
import com.bitpolarity.spotifytestapp.TestingActivity;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Profile_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab.Rooms_Fragment;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;

import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModel;
import com.bitpolarity.spotifytestapp.databinding.ActivityMainHolderBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    LinearLayout standard;
    SpotifyViewModel viewModel;


    //Layout
    RelativeLayout miniRL;

   //ViewBinidings
    ActivityMainHolderBinding binding;
    SpotifyViewModel spotify_viewModel;


    ImageButton playback;
    ImageButton side_navigation_button;
    ImageView miniPlayer_bg;


    TextView mSongName, mArtistName;
    TextView sigmo_Title;
    TextView room;

    ImageView peacock_symbol;
    ImageView cir;
    ImageView Fav;



    //Fragments
    final Fragment fragment1 = new Circle_Fragment();
    final Fragment fragment2 = new Rooms_Fragment();
    final Fragment fragment3 = new Profile_Fragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;




    /////////////////////// SPOTIFY

    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;


    ////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        fm.beginTransaction().add(R.id.fragmentContainerView, fragment3, "3").hide(fragment3).commit();
//        fm.beginTransaction().add(R.id.fragmentContainerView, fragment2, "2").hide(fragment2).commit();
//        fm.beginTransaction().add(R.id.fragmentContainerView,fragment1, "1").commit();


        binding = ActivityMainHolderBinding.inflate(getLayoutInflater());
        standard = findViewById(R.id.linearLayout);

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new SpotifyViewModelFactory(getApplication())).get(SpotifyViewModel.class);


        //////////////////////////////////////// Init5ializations ///////////////////////////////////////////////////

        //ViewBindings
        //miniSongPlayerBinding = MiniSongPlayerBinding.inflate(getLayoutInflater());


        //ViewModel
        //spotify_viewModel = ViewModelProviders.of(this).get(Spotify_ViewModel.class);

        //Models

        //Buttons
        playback = findViewById(R.id.playback);
        miniPlayer_bg = findViewById(R.id.miniplayer_bg);

        side_navigation_button = findViewById(R.id.imageButton);

        //TextViews
        mSongName = findViewById(R.id.m_song_name);
        mArtistName = findViewById(R.id.mArtist_name);
        //sigmo_Title = findViewById(R.id.sigmoTitleBar);

        //ImageViews
        cir = findViewById(R.id.cir);
        Fav = findViewById(R.id.add_to_fav);
        peacock_symbol = findViewById(R.id.bitpSymbl);

        //System services

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Object types

        bottomNavigation = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


//        Glide.with(MainHolder.this)
//                .load("https://picsum.photos/900/700")
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(miniPlayer_bg);
        //////////////////////////////////////// Initializations//////////////////////////////////////////////


    }

    @Override
    public void onStart() {

        super.onStart();

      //  standard.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                peacock_symbol.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.rotate));
//        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.fade_in));
//        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.slidein_left_to_right));
//
//            }
//        }).start();

        mSongName = findViewById(R.id.m_song_name);
        mArtistName = findViewById(R.id.mArtist_name);
        sigmo_Title = findViewById(R.id.sigmoTitleBar);
        room = findViewById(R.id.Rooms);

        detail_holder = new mDetail_Holder();





//        ConnectionParams connectionParams =
//                new ConnectionParams.Builder(CLIENT_ID)
//                        .setRedirectUri(REDIRECT_URI)
//                        .showAuthView(true)
//                        .build();
//
//            SpotifyAppRemote.connect(MainHolder.this, connectionParams,
//                    new Connector.ConnectionListener() {
//
//                        public void onConnected(SpotifyAppRemote spotifyAppRemote) {
//                            mSpotifyAppRemote = spotifyAppRemote;
//                            Log.d("Spotify_Handler", "Connected! Yay!");
//                            connected();
//
//                        }
//
//                        public void onFailure(Throwable throwable) {
//                            Log.e("Spotify_Handler", throwable.getMessage(), throwable);
//                        }
//                    });






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

            switch (item.getItemId()) {

                case R.id.nav_circle:
                    fm.beginTransaction().replace(R.id.fragmentContainerView,fragment1).commit();
                    active = fragment1;


                    if (sigmo_Title.getVisibility() == View.GONE) {
//                binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
                        sigmo_Title.setVisibility(View.VISIBLE);
//                    binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left_to_right_fr));
                        peacock_symbol.setVisibility(View.VISIBLE);
//                    binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                        room.setVisibility(View.GONE);

                }
                    return true;

                case R.id.nav_rooms :
                    fm.beginTransaction().replace(R.id.fragmentContainerView,fragment2).commit();
                    active = fragment2;

//               binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                    sigmo_Title.setVisibility(View.GONE);
//                binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left_fr));
                    peacock_symbol.setVisibility(View.GONE);
//
//                binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
                    room.setVisibility(View.VISIBLE);

                    return true;


                case  R.id.nav_profile:
                    fm.beginTransaction().replace(R.id.fragmentContainerView,fragment3).commit();
                    active = fragment3;
                    return true;
                }


            return true;
        });


        viewModel.getTrackName().observe(this, new Observer<String>(){
                    @Override
                    public void onChanged(String s) {

                        mSongName.setText(s);
                    }
                }
        );

        viewModel.getTrackArtist().observe(this, new Observer<String>(){
                    @Override
                    public void onChanged(String s) {

                        mArtistName.setText(s);
                    }
                }
        );


        viewModel.getImageURI().observe(this, new Observer<String>(){
                    @Override
                    public void onChanged(String s) {

                        Glide.with(MainHolder.this)
                                .load(s)
                                .apply(new RequestOptions().override(50, 50))
                                .into(cir);

                    }
                }

        );



        Fav.setOnClickListener(view -> {

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
                                    Fav.setImageResource(R.drawable.ic_heart);
                                    Fav.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_in));
                                    liked = true;
                                }
                                else{
                                    mSpotifyAppRemote.getUserApi().removeFromLibrary(track.uri);
                                    Fav.setImageResource(R.drawable.ic_fav);
                                    Fav.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_out));

                                    liked = false;
                                }

                            }
                        });


                        if(playerState.isPaused){
                            Log.d(TAG, "connected: paused ");
                           playback.setImageResource(R.drawable.ic_play);
                            playback.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in));

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

