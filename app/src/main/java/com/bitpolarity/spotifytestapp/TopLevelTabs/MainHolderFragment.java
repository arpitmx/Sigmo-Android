package com.bitpolarity.spotifytestapp.TopLevelTabs;

import static com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository.compressImage;
import static com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository.mSpotifyAppRemote;
import static com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository.track;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import com.bitpolarity.spotifytestapp.Spotify.SongModel;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle.StatusFragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Music_fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab.Rooms_Fragment;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyHandler.mDetail_Holder;

import com.bitpolarity.spotifytestapp.databinding.ActivityMainHolderBinding;

import com.bitpolarity.spotifytestapp.databinding.CustomActionBarBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainHolderFragment extends Fragment {


    BottomNavigationView bottomNavigation; // Bottom navigation bar
    public DrawerLayout drawerLayout;   //Side Navigation bar
    public ActionBarDrawerToggle actionBarDrawerToggle; // Toggle for Side navigation bar

    mDetail_Holder detail_holder;
    String TAG = "MainHolder";
    boolean liked = false;

   //ViewBinidings


    ActivityMainHolderBinding binding;
    ImageButton playback;
    CircleImageView side_navigation_button;
    ImageView miniPlayer_bg;

    AppBarConfiguration appBarConfiguration;


    TextView mSongName, mArtistName;
    TextView sigmo_Title;
    TextView room;

    ImageView peacock_symbol;
    ImageView cir;
    ImageView Fav;

    NavOptions navOptions ;


    public static SeekBar playerSeekbar;

    //Fragments
    final Fragment fragment1 = new StatusFragment();
    final Fragment fragment2 = new Rooms_Fragment();
    final Fragment fragment3 = new Music_fragment();
    FragmentManager fm ;
    Fragment active = fragment1;
    ViewPager2 viewPager2;

    CustomActionBarBinding customActionBarBinding;




    /////////////////////// SPOTIFY

    private static final String CLIENT_ID = "84b37e8b82e2466c9f69a2e41b100476";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";


    ////////////////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        fm.beginTransaction().add(R.id.fragmentContainerView, fragment3, "3").hide(fragment3).commit();
//        fm.beginTransaction().add(R.id.fragmentContainerView, fragment2, "2").hide(fragment2).commit();
//        fm.beginTransaction().add(R.id.fragmentContainerView,fragment1, "1").commit();

        //////////////////////////////////////// Init5ializations ///////////////////////////////////////////////////


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityMainHolderBinding.inflate(getLayoutInflater());


        //Buttons

        playback = binding.toolbar.playback;
        miniPlayer_bg = binding.toolbar.miniplayerBg;
        side_navigation_button = binding.customAction.imageButton;
        customActionBarBinding = binding.customAction;
        viewPager2 = getActivity().findViewById(R.id.viewpager);

        //TextViews
        mSongName = binding.toolbar.mSongName;
        mArtistName = binding.toolbar.mArtistName;
        //sigmo_Title = findViewById(R.id.sigmoTitleBar);

        //ImageViews
        cir = binding.toolbar.cir;
        Fav = binding.toolbar.addToFav;
        peacock_symbol = binding.customAction.bitpSymbl;

        //System services

        //Object types


        bottomNavigation = binding.bottomNav;
        drawerLayout = binding.myDrawerLayout;

      appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_circle, R.id.nav_rooms, R.id.nav_music )
                .build();



        //   actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


//        playerSeekbar.setEnabled(true);
//        playerSeekbar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//        playerSeekbar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);

        //////////////////////////////////////// Initializations//////////////////////////////////////////////


        mSongName = binding.toolbar.mSongName;
        mArtistName = binding.toolbar.mArtistName;
        sigmo_Title = binding.customAction.sigmoTitleBar;
        room = binding.customAction.Rooms;

        //navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
      //  NavigationUI.setupWithNavController(bottomNavigation,navController);

//        navOptions= new NavOptions.Builder()
//                .setLaunchSingleTop(true)
//                .setEnterAnim(R.anim.fade_in)
//                .setExitAnim(R.anim.fade_out)
//                .setPopEnterAnim(R.anim.pop_in)
//                .setPopExitAnim(R.anim.pop_out)
//                .setPopUpTo(navController.getGraph().getStartDestination(), false)
//                .build();

        fm = getParentFragmentManager();
        detail_holder = new mDetail_Holder();

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {

        super.onStart();
        side_navigation_button.setOnClickListener(view -> viewPager2.setCurrentItem(0));


        //  standard.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));

//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                peacock_symbol.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.rotate));
//        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.fade_in));
//        sigmo_Title.setAnimation(AnimationUtils.loadAnimation(MainHolder.this, R.anim.slidein_left_to_right));
//
//            }
//        }).start();




      //  drawerLayout.addDrawerListener(actionBarDrawerToggle);
      //  actionBarDrawerToggle.syncState();


        /////////////////////////// OnClick Listeners

       // side_navigation_button.setOnClickListener(view -> drawerLayout.open());
//
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {

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

                 //   navController.navigate(R.id.nav_circle,null,navOptions);

                   fm.beginTransaction().replace(R.id.nav_host_fragment, fragment1).addToBackStack(null).commit();
                    active = fragment1;
                    customActionBarBinding.customActionBarConsLay.setVisibility(View.VISIBLE);

//                    if (sigmo_Title.getVisibility() == View.GONE) {
//               // binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
//                       // sigmo_Title.setVisibility(View.VISIBLE);
//                //   binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left_to_right_fr));
//
//                       // binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
//                        //room.setVisibility(View.GONE);
//                    }

                    peacock_symbol.setVisibility(View.VISIBLE);
                    customActionBarBinding.sigmoTitleBar.setText("Sigmo");
                    return true;


                case R.id.nav_rooms:

                    //navController.navigate(R.id.nav_rooms,null,navOptions);

                   fm.beginTransaction().replace(R.id.nav_host_fragment, fragment2).addToBackStack(null).commit();
                   active = fragment2;
//                    customActionBarBinding.customActionBarConsLay.setVisibility(View.VISIBLE);

               //binding.customAction.sigmoTitleBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
                    //sigmo_Title.setVisibility(View.GONE);
                    customActionBarBinding.sigmoTitleBar.setText("Rooms");
                    //binding.customAction.bitpSymbl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left_fr));
                    peacock_symbol.setVisibility(View.GONE);

                   // binding.customAction.Rooms.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
                    //room.setVisibility(View.VISIBLE);



                    return true;

                case R.id.nav_music:

                  //  navController.navigate(R.id.nav_music,null,navOptions);

                   fm.beginTransaction().replace(R.id.nav_host_fragment, fragment3).addToBackStack(null).commit();
                    active = fragment3;
                    customActionBarBinding.customActionBarConsLay.setVisibility(View.GONE);
                    //peacock_symbol.setVisibility(View.GONE);

                    return true;
            }



            return true;
        });

//        bottomNavigation.setOnNavigationItemReselectedListener(item ->{
//            return @setOnNavigationItemReselectedListener;
//        }
        setMiniPlayerDetails();
        setPosterAndPallet();
        //setPalette();
        setPlayerState();


        Fav.setOnClickListener(view -> Fav_clicked());

    }


    ////////////////////////////////////////////////////////////////////////////////// Handlers

    void setMiniplayerTextColor(){


        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableFromUrl("https://i.scdn.co/image/ab67616d00001e028155c99a241d4c57b2c3f88d"));

        Bitmap b  = getBitmapFromURL("https://i.scdn.co/image/ab67616d00001e028155c99a241d4c57b2c3f88d");
        Palette.from(b).maximumColorCount(12).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Get the "vibrant" color swatch based on the bitmap
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null) {
                    miniPlayer_bg.setBackgroundColor(vibrant.getRgb());

                    mSongName.setTextColor(vibrant.getTitleTextColor());
                    mArtistName.setTextColor(vibrant.getTitleTextColor());

                    Log.d(TAG, "onGenerated: RGB "+vibrant.getBodyTextColor());
                    int Rgb = vibrant.getRgb();

                }
            }
        });

    }


    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;
        HttpURLConnection connection = (HttpURLConnection) new URL(url)
                .openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);


    }

    void setMiniPlayerBGPicassio(){
    //"https://i.scdn.co/image/ab67616d00001e028155c99a241d4c57b2c3f88d"

        Picasso.with(getContext())
                .load("")
                .resize(200, 100)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap)
                                .generate(palette -> {

                                    assert palette != null;
                                    Palette.Swatch textSwatch = palette.getVibrantSwatch();


                                    if (textSwatch == null) {
                                        Log.d(TAG, "onBitmapLoaded: Color"+null);
                                        return;
                                    }

                                    Log.d(TAG, "onBitmapLoaded: color" + textSwatch.getRgb());
                                    miniPlayer_bg.setBackgroundColor(textSwatch.getRgb());
                                    mSongName.setTextColor(textSwatch.getBodyTextColor());
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                            Toast.makeText(getContext(), "Error loading ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /// Handlers


    private void setMiniPlayerDetails(){

        SongModel.getTrackName().observe(this, s -> {
            mSongName.setText(s);
            if(s.equals("Advertisement")){

                //this.prevVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Toast.makeText(getContext() , "Muting ads",Toast.LENGTH_SHORT   ).show();
                mSpotifyAppRemote.getConnectApi().connectSetVolume(0f);

            }
        });

        SongModel.getTrackArtist().observe(this, s -> mArtistName.setText(s));


    }


    void setPosterAndPallet(){

        SongModel.getImgURI().observe(this, imageUri -> mSpotifyAppRemote.getImagesApi().getImage(imageUri).setResultCallback(data -> {
            cir.setImageBitmap(data);
            miniPlayer_bg.setImageBitmap(compressImage(data));
        }));

    }




//    void setPalette(){
//        SongModel.getTrackPalette().observe(this, integer -> {
//
//            //miniPlayer_bg.setBackgroundColor(integer);
//          // miniPlayer_bg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
//
//        });
//
//    }


    public static Bitmap getBitmapFromURL(String src) {
        new Thread(() -> {

        }).start();
        try {
            URL url = new URL(src);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    void setPlayerState(){
        SongModel.getPlayerState().observe(this, aBoolean -> {

           ////////////////////////////////////////////////////////////////////////////////////////

            if(aBoolean){
                Log.d(TAG, "connected: paused ");
                playback.setImageResource(R.drawable.ic_play);
                playback.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

                playback.setScaleX(1f);
                playback.setScaleY(1f);

            }else{
                Log.d(TAG, "connected: playing ");

                playback.setImageResource(R.drawable.ic_baseline_pause_24);
                playback.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_switch));

                playback.setScaleX(1.3f);
                playback.setScaleY(1.6f);

            }

            ///////////////////////////////////////////////////////////////////////////////


            playback.setOnClickListener(view -> {
                if(!aBoolean){
                    Log.d(TAG, "connected: playing ");
                    playback.setImageResource(R.drawable.ic_baseline_pause_24);
                    playback.setAnimation(AnimationUtils.loadAnimation(getContext() , R.anim.fade_in_switch));
                    playback.setScaleX(1.3f);
                    playback.setScaleY(1.6f);
                    mSpotifyAppRemote.getPlayerApi().pause();
                }
                else{
                    playback.setImageResource(R.drawable.ic_play);
                    Log.d(TAG, "connected: paused ");

                    playback.setAnimation(AnimationUtils.loadAnimation(getContext() , R.anim.fade_in_switch));

                    playback.setScaleX(1f);
                    playback.setScaleY(1f);
                    mSpotifyAppRemote.getPlayerApi().resume();

                }
            });
        });


    }


    void Fav_clicked(){


        if (track != null) {

                    if (!liked) {
                        mSpotifyAppRemote.getUserApi().addToLibrary(track.uri);
                        Fav.setImageResource(R.drawable.ic_heart);
                        Fav.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.pop_in));
                        liked = true;

                        Toast.makeText(getContext(), "Added to library", Toast.LENGTH_SHORT).show();

                    } else {
                        mSpotifyAppRemote.getUserApi().removeFromLibrary(track.uri);
                        Fav.setImageResource(R.drawable.ic_fav);
                        Fav.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.pop_out));

                        liked = false;
                        Toast.makeText(getContext(), "Removed from library", Toast.LENGTH_SHORT).show();

                    }
                    }

}



}
