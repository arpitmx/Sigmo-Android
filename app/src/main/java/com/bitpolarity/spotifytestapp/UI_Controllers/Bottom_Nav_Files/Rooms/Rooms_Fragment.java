package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bitpolarity.spotifytestapp.R;


public class Rooms_Fragment extends Fragment {
    AppCompatButton startRoomBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        startRoomBTN =v.findViewById(R.id.startroomBTN);

        startRoomBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RoomStarterActivity.class));
            }
        });



        return v;
    }
}