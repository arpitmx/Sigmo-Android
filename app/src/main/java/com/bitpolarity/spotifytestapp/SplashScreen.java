package com.bitpolarity.spotifytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bitpolarity.spotifytestapp.UI_Controllers.Login;

public class SplashScreen extends AppCompatActivity {

    SpotifySDK sdk ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_splash_screen);
        //setTheme(R.style.SplashTheme);

        Handler handler = new Handler();
        Intent i = new Intent(this, Login.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //sdk = new SpotifySDK(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();

            }
        };

        handler.postDelayed(runnable,1000);



    }
}