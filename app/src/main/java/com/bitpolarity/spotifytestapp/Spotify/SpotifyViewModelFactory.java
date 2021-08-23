package com.bitpolarity.spotifytestapp.Spotify;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SpotifyViewModelFactory implements ViewModelProvider.Factory {

   Application context;

    public SpotifyViewModelFactory(Application context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SpotifyViewModel(context);

    }
}
