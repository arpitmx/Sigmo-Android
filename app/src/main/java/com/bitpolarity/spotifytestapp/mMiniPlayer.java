package com.bitpolarity.spotifytestapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.MainHolder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class mMiniPlayer extends BottomSheetDialogFragment {

    ImageButton playback;
    Context context;


    public mMiniPlayer(Context context){

            this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.mini_song_player,container, false);

        playback = v.findViewById(R.id.playback);

        playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Playback", Toast.LENGTH_SHORT).show();
                Log.d("mini", "onClick: "+"clicked");
            }
        });

        return v;
    }
}