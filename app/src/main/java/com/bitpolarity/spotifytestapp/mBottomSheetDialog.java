package com.bitpolarity.spotifytestapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class mBottomSheetDialog extends BottomSheetDialogFragment {


    String details ;
    WebView spotifyWebView;
    ShimmerFrameLayout shimmerFrameLayout;
    final int height = 80;

    mBottomSheetDialog(String details){
        this.details = details;
       // tempDataHolder = new TempDataHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.song_detail_bottom_sheet,container, false);

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slidein_left_to_right);
        spotifyWebView = v.findViewById(R.id.spotifyWebView);


        WebSettings webViewSettings = spotifyWebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.getLoadsImagesAutomatically();
        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);

        String data = "<iframe src=\"https://open.spotify.com/embed/track/2bgTY4UwhfBYhGT4HUYStN\"" +
                " width=\"320\" height=\""+height+"\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";
        spotifyWebView.loadData(data,"text/html","UTF-8");


                spotifyWebView.setVisibility(View.VISIBLE);
                spotifyWebView.setAnimation(anim);
                //shimmerFrameLayout.setVisibility(View.GONE);




        TextView songD = v.findViewById(R.id.songD);
        songD.setText(details);

        return v;
    }
}