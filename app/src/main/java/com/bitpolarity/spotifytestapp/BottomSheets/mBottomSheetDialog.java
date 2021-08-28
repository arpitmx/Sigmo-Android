package com.bitpolarity.spotifytestapp.BottomSheets;

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
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.SongDetailBottomSheetBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.snapshot.IndexedNode;

public class mBottomSheetDialog extends BottomSheetDialogFragment {


    SongDetailBottomSheetBinding binding;
    String details ;
    String trackID ;

    WebView spotifyWebView;
    final int height = 250;
    Animation anim;

    public mBottomSheetDialog(String details,String trackID){
        this.details = details;
        this.trackID = trackID;
       // tempDataHolder = new TempDataHolder();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //View v = inflater.inflate(R.layout.song_detail_bottom_sheet,container, false);
        binding = SongDetailBottomSheetBinding.inflate(inflater);


       // binding.shimmerFrameLayout.startShimmerAnimation();
        binding.shimmerFrameLayout.setVisibility(View.GONE);
        spotifyWebView = binding.spotifyWebView;

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.pop_in);

        WebSettings webViewSettings = binding.spotifyWebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.getLoadsImagesAutomatically();
        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String mTrackID = trackID.replace("spotify","").replace(":","").replace("track","").trim();
        String data = "<iframe src=\"https://open.spotify.com/embed/track/"+mTrackID+"\"" +
                " width=\"320\" height=\""+height+"\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";


        spotifyWebView.loadData(data,"text/html","UTF-8");

        spotifyWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible (WebView view, String url){
            }
        });


        final int interval = 500; // 0.8 Second
        Handler handler = new Handler();
        Runnable runnable = () -> {


            // binding.shimmerFrameLayout.stopShimmerAnimation();
            //binding.shimmerFrameLayout.setVisibility(View.GONE);

            spotifyWebView.setAnimation(anim);
            spotifyWebView.setVisibility(View.VISIBLE);

        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);


        binding.songD.setText(details);
        binding.linkTospotifyBTN.setOnClickListener(view1 -> {
            Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(trackID) );
            startActivity(launcher);
        });

    }
}