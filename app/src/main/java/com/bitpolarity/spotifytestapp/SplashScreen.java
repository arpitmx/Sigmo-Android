package com.bitpolarity.spotifytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository;
import com.bitpolarity.spotifytestapp.UI_Controllers.Login;
import com.bitpolarity.spotifytestapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    SpotifySDK sdk ;
    ActivitySplashScreenBinding binding;
    static final int delay = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SpotifyRepository spotifyModel;
        spotifyModel = new SpotifyRepository(this);
        spotifyModel.onStart();


        Handler handler = new Handler();

        //1 +2s131ms Theme
        //2 +2s927ms W
        //3 +2s278ms w +334ms

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //binding.heart.setAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
       // binding.imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        //sdk = new SpotifySDK(this);


        Runnable runnable = () -> {
            startActivity(new Intent(this, Login.class));
            finish();
        };

        handler.postDelayed(runnable,delay);



    }
}