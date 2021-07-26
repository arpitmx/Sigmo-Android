package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.MainRoom.MainHolder.RoomHolderActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;


public class mCreateRoomBottomSheet extends BottomSheetDialogFragment {
    AppCompatButton startRoom;
    DB_Handler db_handler;
    TextInputEditText mRoomNameEditText;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db_handler = new DB_Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_room_starter, container , true);
    startRoom = v.findViewById(R.id.startMainRoomBTN);
    mRoomNameEditText = v.findViewById(R.id.editTextROOM);


    startRoom.setOnClickListener(view->{
        String roomName = mRoomNameEditText.getText().toString();
        if(roomName.length()>0) {
            db_handler.CreateRoom(roomName);
            Toast.makeText(getContext(), "Room created" , Toast.LENGTH_SHORT).show();
            Log.d("Bottom", "onCreateView: "+"Room created");
           // startActivity(new Intent(getContext(), RoomHolderActivity.class));
        }else{
            mRoomNameEditText.setError("Comm'on! give it a name, can't b invisible");
        }
    });
        return v;
    }
}

