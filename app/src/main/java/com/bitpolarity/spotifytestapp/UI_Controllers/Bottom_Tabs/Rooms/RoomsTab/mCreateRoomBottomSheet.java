package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder.RoomHolderActivity;
import com.bitpolarity.spotifytestapp.databinding.ActivityRoomStarterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;


public class mCreateRoomBottomSheet extends BottomSheetDialogFragment {
    AppCompatButton startRoom;
    DB_Handler db_handler;
    TextInputEditText mRoomNameEditText;
    ActivityRoomStarterBinding  binding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_handler = new DB_Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = ActivityRoomStarterBinding.inflate(inflater,container,false);

    startRoom = binding.startMainRoomBTN;
    mRoomNameEditText = binding.editTextROOM;
    return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startRoom.setOnClickListener(view1->{

            String roomName = mRoomNameEditText.getText().toString();
            if(roomName.length()>0) {
                if(roomName.length()<15) {
                    db_handler.CreateRoom(roomName);
                    Toast.makeText(getContext(), "Room created", Toast.LENGTH_SHORT).show();
                    Log.d("Bottom", "onCreateView: " + "Room created");
                    Intent i = new Intent(getContext(), RoomHolderActivity.class);
                    i.putExtra("room_name", roomName);
                    dismiss();
                    startActivity(i);

                }else{
                    mRoomNameEditText.setError("Halt right there speedy , make it shorter!");
                }
            }else{
                mRoomNameEditText.setError("Com'mon! give it a dope name!");
            }
        });

        binding.include.cancelRoomCreation.setOnClickListener(view12 -> {
           dismiss();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

