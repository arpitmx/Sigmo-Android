package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.BottomSheet;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Profile_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.MainHolder.RoomMainFragment;


public class Rooms_Fragment extends Fragment {
    AppCompatButton startRoomBTN;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        startRoomBTN =v.findViewById(R.id.startroomBTN);

        startRoomBTN.setOnClickListener(view -> {

            mCreateRoomBottomSheet bottomSheet = new mCreateRoomBottomSheet();
            bottomSheet.show(getChildFragmentManager(), "Room_Bottomsheet up");

                  });



        return v;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}