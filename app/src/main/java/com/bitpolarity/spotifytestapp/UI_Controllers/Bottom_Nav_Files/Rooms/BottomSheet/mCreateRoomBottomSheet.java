package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.BottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.MainHolder.RoomMainFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class mCreateRoomBottomSheet extends BottomSheetDialogFragment {

    AppCompatButton startRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_room_starter, container , true);
    startRoom = v.findViewById(R.id.startMainRoomBTN);

//    startRoom.setOnClickListener(view->{
//        FragmentManager manager = getChildFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.container,new Circle_Fragment(),"e");
//        transaction.addToBackStack(null);
//        transaction.commit();
//    });

    return v;

    }
}

