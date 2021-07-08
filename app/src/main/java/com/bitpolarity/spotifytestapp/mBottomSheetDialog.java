package com.bitpolarity.spotifytestapp;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.snapshot.IndexedNode;

public class mBottomSheetDialog extends BottomSheetDialogFragment {


    String details ;
    String trackID ;

    WebView spotifyWebView;
    ShimmerFrameLayout shimmerFrameLayout;
    final int height = 250;
    TextView linkToSpotify ;

    mBottomSheetDialog(String details,String trackID){
        this.details = details;
        this.trackID = trackID;
       // tempDataHolder = new TempDataHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.song_detail_bottom_sheet,container, false);

        shimmerFrameLayout = v.findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        spotifyWebView = v.findViewById(R.id.spotifyWebView);
        String mTrackID = trackID.replace("spotify","").replace(":","").replace("track","").trim();
        linkToSpotify = v.findViewById(R.id.linkTospotifyBTN);
        String openLink = "https://open.spotify.com/track/4GKcaqt6PFor4siHXMO42e?si=FlnDCX-kR36KndA1zoP2MA&utm_source=copy-link&dl_branch=1";


        WebSettings webViewSettings = spotifyWebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.getLoadsImagesAutomatically();
        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);



        String data = "<iframe src=\"https://open.spotify.com/embed/track/"+mTrackID+"\"" +
                " width=\"320\" height=\""+height+"\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";
        Log.d("mBottomSheetDialog", "trackID: "+mTrackID);

        spotifyWebView.loadData(data,"text/html","UTF-8");


        final int interval = 900; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {

                shimmerFrameLayout.setVisibility(View.GONE);
                spotifyWebView.setVisibility(View.VISIBLE);
                spotifyWebView.setAnimation(anim);


            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
        //shimmerFrameLayout.setVisibility(View.GONE);

        TextView songD = v.findViewById(R.id.songD);
        songD.setText(details);


        linkToSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(trackID) );
                startActivity(launcher);
            }

        });

        return v;
    }
}