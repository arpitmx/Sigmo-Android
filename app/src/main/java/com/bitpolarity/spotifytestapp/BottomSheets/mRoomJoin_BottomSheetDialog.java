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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.RoomJoinBottomSheetBinding;
import com.bitpolarity.spotifytestapp.databinding.SongDetailBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

 public class mRoomJoin_BottomSheetDialog extends BottomSheetDialogFragment {


    RoomJoinBottomSheetBinding binding;
    String roomNAME,totalMEMBERS, TIME, HOSTNAME ;

    public mRoomJoin_BottomSheetDialog(String roomName, String hostname, String totalmembers , String time){
        roomNAME = roomName;
        totalMEMBERS = totalmembers;
        TIME = time;
        HOSTNAME = hostname;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = RoomJoinBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.roomNameBs.setText(roomNAME);
        binding.hostNameBs.setText("Hosted by "+HOSTNAME);
        binding.time.setText(TIME+" ⌚");
        binding.totalMembersBs.setText("23/50 members " +" \uD83D\uDC4B");

    }



}