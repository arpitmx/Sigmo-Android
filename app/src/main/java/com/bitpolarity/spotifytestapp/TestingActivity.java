package com.bitpolarity.spotifytestapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.Spotify.SongModel;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModel;
import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModelFactory;

public class TestingActivity extends AppCompatActivity {

    TextView tName, tArtist, tURI;
    SpotifyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        tName = findViewById(R.id.txt_trackName);
        tArtist = findViewById(R.id.txt_trackArtist);
        tURI = findViewById(R.id.txt_imgURI);

        viewModel = new ViewModelProvider(this, new SpotifyViewModelFactory(getApplication())).get(SpotifyViewModel.class);
    }

//    final Observer<String> trackNameObserver = new Observer<String>() {
//        @Override
//        public void onChanged(@Nullable String newName) {
//            tName.setText(newName);
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();



    }
}