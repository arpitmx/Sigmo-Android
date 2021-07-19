package com.bitpolarity.spotifytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bitpolarity.spotifytestapp.UI_Controllers.Login;
import com.bitpolarity.spotifytestapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    SpotifySDK sdk ;
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler handler = new Handler();


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.heart.setAnimation(AnimationUtils.loadAnimation(this,R.anim.rotate_fadein_fast));

        //sdk = new SpotifySDK(this);


        Runnable runnable = () -> {
            startActivity(new Intent(this, Login.class));
            finish();
        };

        handler.postDelayed(runnable,1000);



    }
}