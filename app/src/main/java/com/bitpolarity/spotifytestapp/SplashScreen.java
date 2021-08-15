package com.bitpolarity.spotifytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyRepository;
import com.bitpolarity.spotifytestapp.UI_Controllers.Login;
import com.bitpolarity.spotifytestapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    static final int delay = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.SplashTheme);

        Handler handler = new Handler();
        Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        //1 +2s131ms Theme
        //2 +2s927ms W
        //3 +2s278ms w +334ms

      // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //binding.heart.setAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
       // binding.imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        //sdk = new SpotifySDK(this);


        Runnable runnable = () -> {
            startActivity(new Intent(this, Login.class));
            Toast.makeText(this, "main holder started", Toast.LENGTH_SHORT).show();
            finish();
        };

        handler.postDelayed(runnable,delay);



    }
}